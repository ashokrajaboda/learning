import React, { lazy } from 'react';
import {
    BrowserRouter as Router,
    Routes,
    Route,
    Link
} from "react-router-dom";
import Home from './pages/Home';

//Products
//const Identity = lazy(() => import("./pages/HomePage"));

//Pages
//const HomePage = lazy(() => import('./pages/HomePage'));
//const Policies = lazy(() => import("./pages/ProfilePage"));
//const Terms = lazy(() => import("./pages/UserProfile"));
////const ContactUs = lazy(() => import("./pages/UserProfile"));
//const AboutUs = lazy(() => import("./pages/UserProfile"));
//const NotFound = lazy(() => import("./pages/UserProfile"));

//User
/*
const AuthContainer = lazy(() => import("./screens/User/AuthContainer"));
const CommsActivations = lazy(() => import("./screens/User/CommsActivations"));
const ProtectedRoute = lazy(() => import("./components/common/ProtectedRoute"));
const PasswordResetLink = lazy(() => import("./screens/User/PasswordResetLink"));
const ChangePassword = lazy(() => import("./screens/User/ChangePassword"));
const AccountInformation = lazy(() => import("./screens/Registration/AccountInformation"));
const Onboarding = lazy(() => import("./screens/Registration/Onboarding"));
const SwitchPlan = lazy(() => import("./screens/User/SwitchPlan"));

//Content
const HowPellerexWorks = lazy(() => import("./screens/Content/Blog/Posts/Pellerex-Overview"));
const BlogList = lazy(() => import("./screens/Content/Blog/BlogList"));
const UrlRewrite = lazy(() => import("./screens/Content/Blog/Posts/API/UrlRewrite"));
*/
const routes = [
    {
        path: "/",
        exact: true,
        sidebar: () => <div>home!</div>,
        main: () => <h2>Home</h2>
    },
    {
        path: "/bubblegum",
        sidebar: () => <div>bubblegum!</div>,
        main: () => <h2>Bubblegum</h2>
    },
    {
        path: "/shoelaces",
        sidebar: () => <div>shoelaces!</div>,
        main: () => <h2>Shoelaces</h2>
    }
];

const AppRouter = () => {
    return (
        <div>AppRouter</div>
    );
};
/*
const AppRouter1 = () => {
    return (
        <Router>
            <div style={{ display: "flex" }}>
                <div
                    style={{
                        padding: "10px",
                        width: "40%",
                        background: "#f0f0f0"
                    }}
                >
                    <ul style={{ listStyleType: "none", padding: 0 }}>
                        <li>
                            <Link to="/">Home</Link>
                        </li>
                        <li>
                            <Link to="/bubblegum">Bubblegum</Link>
                        </li>
                        <li>
                            <Link to="/shoelaces">Shoelaces</Link>
                        </li>
                    </ul>

                    <Routes>
                        {routes.map((route, index) => (
                            // You can render a <Route> in as many places
                            // as you want in your app. It will render along
                            // with any other <Route>s that also match the URL.
                            // So, a sidebar or breadcrumbs or anything else
                            // that requires you to render multiple things
                            // in multiple places at the same URL is nothing
                            // more than multiple <Route>s.
                            <Route
                                key={index}
                                path={route.path}
                                exact={route.exact}
                                children={<route.sidebar />}
                            />
                        ))}
                    </Routes>
                </div>

                <div style={{ flex: 1, padding: "10px" }}>
                    <Routes>
                        {routes.map((route, index) => (
                            // Render more <Route>s with the same paths as
                            // above, but different components this time.
                            <Route
                                key={index}
                                path={route.path}
                                exact={route.exact}
                                children={<route.main />}
                            />
                        ))}
                    </Routes>
                </div>
            </div>
        </Router>
    );
}
*/

const AppRouter2 = () => {
    return (
        <Routes>
            <Route exact path="/" >
                <Home />
            </Route>
        </Routes>
    );
}

export default AppRouter;