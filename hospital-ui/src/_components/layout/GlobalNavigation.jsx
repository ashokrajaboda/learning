import layoutData from './layout_data.json';
import Sidebar from './sidebar/Sidebar';

const GlobalNavigation = (props) => {
    console.log('props :', props);
    return (
       <Sidebar sidebarItems = {layoutData.menuItems}/>
    );
};

export default GlobalNavigation;