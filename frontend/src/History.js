import Header from "./Header";
import {useState} from "react";
import axios from "axios";
import {SERVER_HOST} from "./api";
import "./History.css";

function History() {
    const [year, setYear] = useState(2022);
    const [month, setMonth] = useState(12);
    const [day, setDay] = useState(15);

    const [sort, setSort] = useState("asc");

    const [rewardHistories, setRewardHistories] = useState([]);

    function handleSubmit(e) {
        e.preventDefault();

        const params = {
            date: `${year}-${month}-${day}`, sort_by: sort
        };

        axios.get(SERVER_HOST + '/rewards/histories', {params})
            .then(function (response) {
                console.log(response);

                if (response.data['success']) {
                    setRewardHistories(response.data['histories']);
                } else {
                    alert('실패');
                }
            }).catch(function (error) {
            if (error.response) {
                alert('실패\n' + error.response.data['message']);
            } else {
                alert('실패\n' + error.toString());
            }

            console.log(error);
        });
    }

    function handleSelect(e) {
        setSort(e.target.value);
    }

    return (
        <div>
            <Header/>
            <div className="container">
                <h2>보상 기록 조회하기</h2>
                <form onSubmit={handleSubmit} id="history_form">
                    <p>연월일:</p>
                    <input
                        type="number"
                        value={year}
                        min={2010}
                        max={2030}
                        onChange={(e) => setYear(parseInt(e.target.value))}
                    />
                    <input
                        type="number"
                        value={month}
                        min={1}
                        max={12}
                        onChange={(e) => setMonth(parseInt(e.target.value))}
                    />
                    <input
                        type="number"
                        value={day}
                        min={1}
                        max={31}
                        onChange={(e) => setDay(parseInt(e.target.value))}
                    />

                    <p>정렬 순서:</p>
                    <select name="sort" id="sort" value={sort} onChange={handleSelect}>
                        <option value="asc">오름차순</option>
                        <option value="desc">내림차순</option>
                    </select>

                    <p></p>
                    <button type="submit">조회하기</button>
                </form>
                <div>
                    {rewardHistories.length > 0 &&
                    <table id="reward_histories">
                        <thead>
                        <tr>
                            <th>시간</th>
                            <th>ID</th>
                            <th>지급한 포인트</th>
                            <th>연속 지급일</th>
                        </tr>
                        </thead>
                        <tbody>
                        {rewardHistories.map(rewardHistory => {
                            return <tr>
                                <td>{rewardHistory['issuedAt'].substring(11)}</td>
                                <td>{rewardHistory['username']}</td>
                                <td>{rewardHistory['amount']}</td>
                                <td>{rewardHistory['consecutiveDay']}</td>
                            </tr>
                        })}
                        </tbody>
                    </table>
                    }
                </div>
            </div>
        </div>);
}

export default History;