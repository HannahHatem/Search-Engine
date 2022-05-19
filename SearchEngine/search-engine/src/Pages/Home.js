import React,{useState} from 'react'
import classes from './Home.module.css'
import { Link } from 'react-router-dom'



import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AppsIcon from '@mui/icons-material/Apps';
import FacebookOutlinedIcon from '@mui/icons-material/FacebookOutlined';
import YouTubeIcon from '@mui/icons-material/YouTube';
import InstagramIcon from '@mui/icons-material/Instagram';
import TwitterIcon from '@mui/icons-material/Twitter';

import logo3 from '../Assets/logo3.png'
import SearchComponent from '../Components/SearchComponent';

let count;
function Home() {
  const onSearch=() => {
    let count=localStorage.getItem('count');
    localStorage.setItem('count',parseInt(count)+1);
  };
  localStorage.setItem('count', 0);
  return (

    <div className={`${classes.home}`}>
      <div className={`${classes.homeHeader}`}>
        <div className={`${classes.homeHeaderLeft}`}>
          <Link to='about'>About</Link>
          <Link to='store'>Store</Link>
        </div>
        <div className={`${classes.homeHeaderRight}`}>
          <Link to='gmail'>Gmail</Link>
          <Link to='images'>Images</Link>
          <AppsIcon className={`mx-5`}/>
          <AccountCircleIcon className={`mx-5`} sx={{ color: '#50c5a8' }}/>


        </div>
      </div>
      <div className={`${classes.homeBody}`}>
        <img src={logo3} alt="" />
        <div className={`${classes.homeInputContainer}`}>
          <SearchComponent searchValue={""} searchPage={false} onChangeSearch={onSearch}/>
        </div>
        <div className={`${classes.homeIconsContainer}`}>
          <div className={`${classes.circularDiv}`}>
          <a href='https://www.facebook.com' target="_blank" ><FacebookOutlinedIcon fontSize="large" className={`${classes.circularIcon}`}/> </a>
          </div>
          <div className={`${classes.circularDiv}`}>
          <a href='https://www.youtube.com/?gl=EG' target="_blank" ><YouTubeIcon fontSize="large" className={`${classes.circularIcon}`}/> </a>
          </div>
          <div className={`${classes.circularDiv}`}>
          <a href='https://www.instagram.com/' target="_blank" ><InstagramIcon fontSize="large" className={`${classes.circularIcon}`}/> </a>
          </div>
          <div className={`${classes.circularDiv}`}>
          <a href='https://twitter.com/' target="_blank" ><TwitterIcon fontSize="large" className={`${classes.circularIcon}`}/></a>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Home