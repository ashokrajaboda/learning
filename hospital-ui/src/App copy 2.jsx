import classNames from 'classnames';
import React, { useContext, useEffect, useState } from 'react';
import { Nav, Navbar, NavDropdown } from 'react-bootstrap';
import styles from './App.scss';
import { Counter } from './features/counter/Counter';
import GlobalNavigation from './_components/layout/GlobalNavigation';
import Body from './_components/layout/Body';
import Footer from './_components/layout/footer/Footer';
import { Provider } from 'react-redux';
import { store } from './store/store';
import AppRouter from './AppRouter';
import { BrowserRouter } from 'react-router-dom';


const App = () => {
  const [isRtl, setIsRtl] = useState(false);
  const [isDarkTheme, setIsDarkTheme] = useState(true);

  const [collapseOnSelect, setCollapseOnSelect] = useState(false);
  const [exclusiveExpand, setExclusiveExpand] = useState(false);

  const [isActive, setActive] = useState(false);
  const [className, setClassName] = useState('d-flex');

  useEffect(() => {
    if (isActive) setClassName('toggled');
    else setClassName('d-flex');
  }, [isActive]);

  const handleToggle = () => {
    setActive(!isActive);
  };

  //const authCtx = useContext(AuthContext);
  return (
    <div className={styles.App} >
      <Provider store={store}>
        <BrowserRouter>
          <GlobalNavigation />
          <div className={styles.Container}>
            <AppRouter />
          </div>
          <Footer />
        </BrowserRouter>
      </Provider>
    </div>
  )

};

export default App;