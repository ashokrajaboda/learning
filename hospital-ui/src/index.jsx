import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import reportWebVitals from './reportWebVitals';
import './index.scss';
import '../node_modules/bootstrap/dist/js/bootstrap.bundle';

import { BrowserRouter as Router } from 'react-router-dom';
//import './styles/styles.scss';

import { store } from './_store';

// setup fake backend
import { configureFakeBackend } from './_helpers';
import App from './App';
configureFakeBackend();

const container = document.getElementById('root');
const root = createRoot(container);

root.render(
  <Provider store={store}>
    <Router>
      <App />
    </Router>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
