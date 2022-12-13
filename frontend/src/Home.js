import Header from "./Header";
import {useEffect, useState} from "react";
import axios from "axios";

function Home() {
    const [notification, setNotification] = useState({
        id: '',
        title: '',
        descriptions: []
    });

    useEffect(() => {
        axios.get("http://localhost:8080/notifications")
            .then(function (response) {
                setNotification(response.data.notifications[0])
            });
    }, []);

    return (
        <div>
            <Header/>
            <h2>{notification.title}</h2>
            <ul>
                {notification.descriptions.map(
                    (d, idx) => <li key={idx}>{d.line}
                        <ul>{d.subline.map(
                            (s, idx) => <li key={idx}>{s}</li>
                        )}</ul>
                    </li>
                )}
            </ul>
        </div>
    );
}

export default Home;