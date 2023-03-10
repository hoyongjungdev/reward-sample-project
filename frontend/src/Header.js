import {Link} from "react-router-dom";
import "./Header.css";

function Header() {
    return (
        <header id="header">
            <h1>선착순 보상 이벤트</h1>
            <ul>
                <li><Link to="/">보상 지급 받기</Link></li>
                <li><Link to="/history">보상 기록</Link></li>
                <li><Link to="/register">회원가입</Link></li>
            </ul>
        </header>
    );
}

export default Header;