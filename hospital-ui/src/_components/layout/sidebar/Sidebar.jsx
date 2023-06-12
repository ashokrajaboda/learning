import SidebarItem from "./SidebarItem";

const Sidebar = ({sidebarItems}) => {

    console.log('sidebarItems :', sidebarItems);
    return (
        <div className="sidebar">
          { sidebarItems.map((item, index) => <SidebarItem key={index} item={item} />) }
        </div>
    );
};

export default Sidebar;