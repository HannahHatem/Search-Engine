import React, { useState } from "react";
import classes from "./SearchComponent.module.css";
import "./SearchComponent.css";
import MicIcon from "@mui/icons-material/Mic";
import SearchIcon from "@mui/icons-material/Search";
import SpeechRecognition, {
  useSpeechRecognition,
} from "react-speech-recognition";
import { useHistory } from "react-router-dom";
import { ReactSearchAutocomplete } from "react-search-autocomplete";

function SearchComponent(props) {
  const { transcript, resetTranscript } = useSpeechRecognition();
  const [searchValue, setSearchValue] = useState(props.searchValue);
  const [startListening, setStartListening] = useState(false);
  const history = useHistory();
// for auto complete
const [searchString, setSearchString] = useState("");



const handleOnSearch = (string, results) => {
  console.log(string, results);
  setSearchString(string);
};

const handleOnClear = () => {
  console.log("Cleared");
  // setSearchString("");
  let items =  [] ;
  let toBesuggested = [] ;
  
     let inLocal;
     let count=localStorage.getItem('count');
     if (localStorage.getItem("items") !== null) {
       inLocal = localStorage.getItem("items");
       console.log(inLocal);
       items = JSON.parse(inLocal);
     }
     
     items.push({count,searchValue});
     localStorage.setItem("items", JSON.stringify(items));
    //  e.preventDefault();
     props.onChangeSearch();
     let c=parseInt(count);
     items.forEach((item) => {
       toBesuggested.push({id:c,name: item.searchValue});
       c++;
     })
     console.log(toBesuggested,"sugg");
     localStorage.setItem("itemss", JSON.stringify(toBesuggested));
     if(props.searchPage===false)
      setSearchValue("");
 
     history.push({ pathname: "/search", state: searchValue });
};

const clearSearchBox = () => {
  setSearchString("");
};
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////

   const search = (e) => {
    let items =  [] ;
    let toBesuggested = [] ;
    let inLocal;
    let count=localStorage.getItem('count');
    if (localStorage.getItem("items") !== null) {
      inLocal = localStorage.getItem("items");
      console.log(inLocal);
      items = JSON.parse(inLocal);
    }
    
    items.push({count,searchValue});
    localStorage.setItem("items", JSON.stringify(items));
    e.preventDefault();
    props.onChangeSearch();
    let c=0;
    items.forEach((item) => {
      toBesuggested.push({id:"c",name: item.searchValue});
      c++;
    })
    console.log(toBesuggested,"sugg");
    localStorage.setItem("itemss", JSON.stringify(toBesuggested));


    history.push({ pathname: "/search", state: searchValue });
   };
  return (
    <form className={`search`}>
      <div className={`searchInput`}>
        <SearchIcon className={`${classes.searchInputIcon}`} />
        <input
          value={searchValue}
          onChange={(e) => {
            setSearchValue(e.target.value);
          }}
          type="text"
        />
        <MicIcon
          className={`${classes.searchInputIcon}`}
          onClick={() => {
            if (!startListening) {
              resetTranscript();
              SpeechRecognition.startListening({ continuous: true });
              setStartListening(true);
              setSearchValue(" ");
            } else {
              SpeechRecognition.stopListening();
              setStartListening(false);
              setSearchValue(transcript);
            }
          }}
        />
      </div>
      <div>
      {/* <ReactSearchAutocomplete
            items={JSON.parse(localStorage.getItem("itemss"))?JSON.parse(localStorage.getItem("itemss")):[]}
            onSearch={handleOnSearch}
            onClear={handleOnClear}
            inputSearchString={searchString}
            autoFocus
          /> */}
      </div>
      <div>
        <button
          className={`${classes.inputButton}`}
          type="submit"
          onClick={search}
        ></button>
      </div>
    </form>
  );
}

export default SearchComponent;
