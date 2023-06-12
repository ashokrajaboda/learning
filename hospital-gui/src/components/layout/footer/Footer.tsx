import React from 'react';

const Footer: React.FC = (props) => {
  return (
    <footer id="footer" className="footer">
      <div className="copyright"> © Copyright 2023 <strong><span>Gagans Pvt Ltd</span></strong>. All Rights Reserved</div>
      <div className="credits"> Designed by <a href="#">Gagans Pvt Ltd</a></div>
    </footer>
  );
};

export default Footer;