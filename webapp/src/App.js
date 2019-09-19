import React from 'react';
import './App.css';
import { Frame } from './frame/Frame';
import { Commits } from './commits/Commits';

function App() {
  return (
    <div className="App">
      <Frame>
        <Commits></Commits>
      </Frame>
    </div>
  );
}

export default App;
