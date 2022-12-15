import './App.css';
import Home from './Home'
import History from './History'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Register from "./Register";

function App() {
    return (
        <BrowserRouter>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/history" element={<History/>}/>
                    <Route path="/register" element={<Register/>}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
