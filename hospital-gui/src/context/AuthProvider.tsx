import { createContext, useState } from "react";

const AuthContext = createContext({});

export const AuthProvider: React.FC<{children: any}> = (props) => {
    const [auth, setAuth] = useState({});

    return (
        <AuthContext.Provider value={{ auth, setAuth }}>
            {props.children}
        </AuthContext.Provider>
    )
}

export default AuthContext;