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
        </div>
    );
}

export default Home;