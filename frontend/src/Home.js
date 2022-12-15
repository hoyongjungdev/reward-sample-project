import Header from "./Header";
import {useEffect, useState} from "react";
import axios from "axios";
import {SERVER_HOST} from "./api";

function Home() {
    const [notification, setNotification] = useState({
        id: '',
        title: '',
        descriptions: []
    });

    const [username, setUsername] = useState("");

    useEffect(() => {
        axios.get(SERVER_HOST + "/notifications")
            .then(function (response) {
                setNotification(response.data.notifications[0])
            })
            .catch(function (error) {
                if (error.response) {
                    alert('실패\n' + error.response.data['message'])
                } else {
                    alert('실패\n' + error.toString());
                }
            });
    }, []);

    function handleSubmit(e) {
        e.preventDefault();

        axios.post(SERVER_HOST + '/rewards', {
            username: username
        }).then(function (response) {
            if (response.data['success']) {
                alert('성공');
            } else {
                alert('실패');
            }
        }).catch(function (error) {
            if (error.response) {
                alert('실패\n' + error.response.data['message'])
            } else {
                alert('실패\n' + error.toString());
            }
        });
    }

    return (
        <div>
            <Header/>
            <h2>{notification.title}</h2>
            <ul>
                {notification.descriptions.map(
                    (description, idx) => <li key={idx}>{description.line}
                        <ul>{description.subline.map(
                            (subline, idx) => <li key={idx}>{subline}</li>
                        )}</ul>
                    </li>
                )}
            </ul>
            <div>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        value={username}
                        placeholder="ID"
                        onChange={(e) => setUsername(e.target.value)}
                    />

                    <button type="submit">보상 지급 받기</button>
                </form>
            </div>
        </div>
    );
}

export default Home;