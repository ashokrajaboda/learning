import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import About from "../../pages/contact/About";
import Contact from "../../pages/contact/Contact";
import NotFound from "../../pages/NotFound";
import Roles from "../../pages/roles/Roles";
import Users from "../../pages/users/Users";
import { IMenuItem, MenuType } from "../../types/model.type";
import BoardAdmin from "../demo/BoardAdmin";
import BoardModerator from "../demo/BoardModerator";
import BoardUser from "../demo/BoardUser";
import Home from "../demo/Home";
import Login from "../demo/Login";
import Profile from "../demo/Profile";
import Register from "../demo/Register";
import Dashboard from "./main/Dashboard";

export const menuItems: (IMenuItem)[] = [
  {
    id: 0,
    key: "home",
    type: MenuType.Menu,
    label: "Home",
    active: true,
    iconStyle: '',
    path: "/",
    elements: [<Home />]
  },
  {
    id: 1,
    key: "login",
    type: MenuType.Menu,
    label: "Login",
    active: true,
    iconStyle: '',
    path: "/login",
    elements: [<Login />]
  },
  {
    id: 2,
    key: "register",
    type: MenuType.Menu,
    label: "Register",
    active: true,
    iconStyle: '',
    path: "/register",
    elements: <Register />
  },
  {
    id: 3,
    key: "dashboard",
    type: MenuType.Menu,
    label: "Dashboard",
    active: true,
    path: "/dashboard",
    iconStyle: '',
    elements: <Dashboard />,
    submenus: [
      {
        id: 31,
        key: "register",
        type: MenuType.Menu,
        label: "Register",
        active: true,
        iconStyle: '',
        path: "/register",
        elements: <Register />
      }
    ]
  },
  {
    id: 4,
    key: "contact",
    type: MenuType.Menu,
    label: "Contact",
    active: true,
    iconStyle: '',
    path: "/contact",
    elements: <Contact />,
    submenus: [
      {
        id: 41,
        key: "about",
        type: MenuType.Menu,
        label: "About",
        active: true,
        iconStyle: '',
        path: "/about",
        elements: <About />
      },
      {
        id: 42,
        key: "profile",
        label: "Profile",
        type: MenuType.Menu,
        active: true,
        iconStyle: '',
        path: "/profile",
        elements: <Profile />
      }
    ]
  },
  {
    id: 5,
    key: "misscelineous",
    type: MenuType.Menu,
    label: "Misc",
    active: true,
    iconStyle: '',
    submenus: [
      {
        id: 51,
        key: "notFound",
        label: "NotFound",
        type: MenuType.Menu,
        active: true,
        iconStyle: '',
        path: "/notFound1",
        elements: <NotFound />,
        submenus: [
          {
            id: 511,
            key: "notFound1",
            label: "NotFound",
            type: MenuType.Menu,
            active: true,
            iconStyle: '',
            path: "/notFound2",
            elements: <NotFound />,
          }
        ]
      }
    ]
  }
];

const AppRoutes = () => {
  const flatten = (menuItems: IMenuItem[]) => {
    return menuItems.reduce((acc: IMenuItem[], r: IMenuItem) => {
      if(r.active && r.type === MenuType.Menu) {
        if(r.path && r.path != '' && r.elements){
          acc.push(r);
        }
        if(r.submenus && r.submenus.length > 0) {
          acc = acc.concat(flatten(r.submenus));
        }
      }
      return acc;
    }, [])
  }

  const nextedRoutes = flatten(menuItems);
  console.log('Nexed Routes: ', nextedRoutes);
  const configuredRoutes = nextedRoutes.map((menu, index) => {
    return <Route path={menu.path} element={menu.elements} key={index}/>
  });
  /*
      <Route path="/" element={<Home />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/profile" element={<Profile />} />
      <Route path="/user" element={<BoardUser />} />
      <Route path="/mod" element={<BoardModerator />} />
      <Route path="/admin" element={<BoardAdmin />} />
      <Route path="/contact" element={<Contact />} />
      <Route path="/about" element={<About />} />
      <Route path="/notFound" element={<NotFound />} />
  */
  return (
    <Routes>
      {configuredRoutes}
    </Routes>
  )
};

export default AppRoutes;