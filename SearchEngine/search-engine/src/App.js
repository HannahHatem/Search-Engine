import React from "react";
import "./App.css";

import Home from "./Pages/Home";
import Search from "./Pages/Search";

import {BrowserRouter, Switch, Redirect, Route } from "react-router-dom";

function App() {
  
  return (
    <div>
      <BrowserRouter forceRefresh={true}>
        <Switch>
          <Route path="/search" component={Search}>
          </Route>
          <Route path="/" component={Home}>
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
