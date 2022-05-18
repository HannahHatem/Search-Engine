import React, { useState } from "react";
import classes from "./SearchComponent.module.css";
import "./SearchComponent.css";
import MicIcon from "@mui/icons-material/Mic";
import SearchIcon from "@mui/icons-material/Search";
import SpeechRecognition, {
  useSpeechRecognition,
} from "react-speech-recognition";
import { useHistory } from "react-router-dom";

function SearchComponent(props) {
  const { transcript, resetTranscript } = useSpeechRecognition();
  const [searchValue, setSearchValue] = useState(props.searchValue);
  const [startListening, setStartListening] = useState(false);
  const history = useHistory();
  const search = (e) => {
    e.preventDefault();
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
              setSearchValue(' ');
          
            } else {
              SpeechRecognition.stopListening();
              setStartListening(false);
              setSearchValue(transcript);

            }
          }}
        />
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
