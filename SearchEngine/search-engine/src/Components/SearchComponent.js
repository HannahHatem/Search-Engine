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
import instance from "./axios";

function SearchComponent(props) {
  const { transcript, resetTranscript } = useSpeechRecognition();
  // const [searchValue, setSearchValue] = useState(props.searchValue);
  const [searchValue, setSearchValue] = useState("");
  const [startListening, setStartListening] = useState(false);
  const history = useHistory();
  // for auto complete
  const [searchString, setSearchString] = useState("");
  const [searchResults, setSearchResults] = useState([]);

  const sleep = (milli) => {
    return new Promise((resolve) => setTimeout(resolve, milli));
  };
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////

  const search = (e) => {
    e.preventDefault();
    console.log("Printing ", searchValue);
    // history.push({
    //   pathname: "/search",
    //   state: searchValue,
    // });
    props.onChangeSearch(searchValue);
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
        >
          Click me
        </button>
      </div>
    </form>
  );
}

export default SearchComponent;
