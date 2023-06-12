import React from "react";
import AppRoutes from "../AppRoutes";
import Dashboard from "./Dashboard";

const Main2: React.FC = (props) => {
  return (
    <main id="main" className="main d-flex flex-column">
      <AppRoutes />
    </main>
  );
};

export default Main2;