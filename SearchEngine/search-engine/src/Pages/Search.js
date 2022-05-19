import React, { useEffect, useState } from "react";

import { Link } from "react-router-dom";
import SearchComponent from "../Components/SearchComponent";
import "./Search.css";

import SearchIcon from "@mui/icons-material/Search";
import ImageIcon from "@mui/icons-material/Image";
import DescriptionIcon from "@mui/icons-material/Description";
import LocalOfferIcon from "@mui/icons-material/LocalOffer";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import RoomIcon from "@mui/icons-material/Room";
import logo3 from "../Assets/logo3.png";
import AppPagination from "../Pagination/AppPagination";
import instance from "../Components/axios";

const DUMMY_RESULTS = [
  {
    id: 1,
    title: "Tesla: Electric Cars, Solar & Clean Energy",
    snippet:
      "Model 3 is designed for electric-powered performance, with dual motor AWD, \nquick acceleration, long range and fast charging.",
    link: "https://finance.yahoo.com/quote/TSLA/",
  },
  {
    id: 2,
    title: "Tesla the Band | Official Website | American Made Rock 'n' Roll",
    snippet:
      "Tesla is accelerating the world's transition to sustainable energy with electric cars\n, solar and integrated renewable energy solutions for homes and businesses.",
    link: "https://www.tesla.com/",
  },
  {
    id: 3,
    title: "Model 3 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 4,
    title: "Teslaa: Electric Cars, Solar & Clean Energy",
    snippet:
      "Model 3 is designed for electric-powered performance, with dual motor AWD, \nquick acceleration, long range and fast charging.",
    link: "https://finance.yahoo.com/quote/TSLA/",
  },
  {
    id: 5,
    title: "Tesla the Band | Official Website | American Made Rock 'n' Roll",
    snippet:
      "Tesla is accelerating the world's transition to sustainable energy with electric cars\n, solar and integrated renewable energy solutions for homes and businesses.",
    link: "https://www.tesla.com/",
  },
  {
    id: 6,
    title: "Model 3 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 7,
    title: "Tesla: Electric Cars, Solar & Clean Energy",
    snippet:
      "Model 3 is designed for electric-powered performance, with dual motor AWD, \nquick acceleration, long range and fast charging.",
    link: "https://finance.yahoo.com/quote/TSLA/",
  },
  {
    id: 8,
    title: "Tesla the Band | Official Website | American Made Rock 'n' Roll",
    snippet:
      "laaaast is accelerating the world's transition to sustainable energy with electric cars\n, solar and integrated renewable energy solutions for homes and businesses.",
    link: "https://www.tesla.com/",
  },
  {
    id: 9,
    title: "Model 3 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 10,
    title: "Model 4 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 11,
    title: "Model 5 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 12,
    title: "Model 6 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
  {
    id: 13,
    title: "Model 7 | Tesla",
    snippet:
      "Tesla's mission is to accelerate the world's transition to sustainable energy. \nToday, Tesla builds not only all-electric vehicles but also infinitely scalable clean\n ...",
    link: "https://www.youtube.com/watch?v=e4U-23TOKms",
  },
];

function Search(props) {
  const { state } = props.location;
  console.log(state);
  const [results, setResults] = useState([]);
  const [searchInput, setSearchInput] = useState(state[0]);
  const [searchResults, setSearchResults] = useState([]);
  let checkHome=state;
  // useEffect(() => {console.log(state)}, [])
  const onSearch = (val) => {
    console.log("Sending request", val);
    try {
     instance
       .get(`/search/?q=${val}`)
       .then((res) => {
         console.log(res.data, "res");
         setSearchResults(res.data.data);
         setResults(res.data.data);
       })
       .catch((err) => {
         console.log(err);
       });
    } catch (err) {
      console.log(err);
    }
    let count = localStorage.getItem("count");
    localStorage.setItem("count", parseInt(count) + 1);
  };

  if(checkHome[1]==="home"){
    checkHome[1]="";
    onSearch(searchInput);
  }

  return (
    <div className={"searchPage"}>
      <div className={`searchPageHeader `}>
        <Link to="/">
          <img className="imageLogo" src={logo3} alt="" />
        </Link>
        <div className="searchBar">
          <SearchComponent
            onChangeSearch={onSearch}
            searchPage={true}
            searchValue={searchInput}
          />
          <div className="searchPage_options">
            <div className="searchPage_optionsLeft">
              <div className="searchPage_option">
                <SearchIcon />
                <Link to="/all">All</Link>
              </div>
              <div className="searchPage_option">
                <DescriptionIcon />
                <Link to="/news">News</Link>
              </div>
              <div className="searchPage_option">
                <ImageIcon />
                <Link to="/images">Images</Link>
              </div>
              <div className="searchPage_option">
                <LocalOfferIcon />
                <Link to="/shopping">Shopping</Link>
              </div>
              <div className="searchPage_option">
                <RoomIcon />
                <Link to="/maps">Maps</Link>
              </div>
              <div className="searchPage_option">
                <MoreVertIcon />
                <Link to="/more">More</Link>
              </div>
            </div>
            <div className="searchPage_optionsRight">
              <div className="searchPage_option">
                <Link to="/settings">Settings</Link>
              </div>
              <div className="searchPage_option">
                <Link to="/tools">Tools</Link>
              </div>
            </div>
          </div>
        </div>
      </div>
      {results.length !== 0 && (
        <div className="searchPage_results">
          {results.map((result) => {
            return (
              <div className="searchPage_result">
                <div className="searchPage_resultTitle">
                  {/* <a href={result.url}>{result.title}</a> */}
                </div>
                <div className="searchPage_resultLink">
                  <a href={result.url}>{result.url}</a>
                </div>
                <div className="searchPage_resultSnippet">
                  {/* {result.description} */}
                </div>
              </div>
            );
          })}
        </div>
      )}
      {searchResults.length>0 && <AppPagination
        documents={searchResults}
        setResults={(r) => {
          setResults(r);
        }}
      />}
    </div>
  );
}

export default Search;
