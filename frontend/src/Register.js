import Header from "./Header";
import {useState} from "react";
import {SERVER_HOST} from "./api";
import axios from "axios";

function Register() {
    const [username, setUsername] = useState("")

    let handleSubmit = (e) => {
        e.preventDefault();

        axios.post(SERVER_HOST + '/users', {
            username: username
        }).then(
            function (response) {
                if (response.data['success']) {
                    alert('성공');
                } else {
                    alert('실패');
                }
            }
        ).catch(function (error) {
            if (error.response) {
                alert('실패\n' + error.response.data['message'])
            } else {
                alert('실패\n' + error.toString());
            }
        });
    };

    return (
        <div>
            <Header/>
            <div className="container">
                <h2>회원가입</h2>
                <form onSubmit={handleSubmit}>
                    <p>
                        <input
                            type="text"
                            value={username}
                            placeholder="ID"
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </p>
                    <p>
                        <button type="submit">회원가입</button>
                    </p>
                </form>
            </div>
        </div>
    );
}

export default Register;