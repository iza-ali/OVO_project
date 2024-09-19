const url = 'http://localhost:8080';
let stompClient;
let gameId;
let username = document.getElementById("username").innerText;
console.log(username);

const csrfToken = $('meta[name="_csrf"]').attr('content');
const csrfHeader = $('meta[name="_csrf_header"]').attr('content');

console.log("connecting to the game");
function connectToSocket(gameId) {

    let socket = new SockJS(url + "/gameplay");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("connected to the frame: " + frame);
        stompClient.subscribe("/topic/game-progress/" + gameId, function (response) {
            let data = JSON.parse(response.body);
            console.log(data);
            displayResponse(data);
        })
    })
}

function create_game() {
    if (username == null || username === '') {
        alert("Please login");
    } else {
        console.log(JSON.stringify({
            "player": username
        }));
        $.ajax({
            url: "/game/start",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "player": username
            }),
            beforeSend: function(xhr) {
                // Add the CSRF token to the request header
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                gameId = data.gameId;
                playerType = 'X';
                reset();
                connectToSocket(gameId);
                alert("Your created a game. Game id is: " + data.gameId);
                gameOn = true;
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}


function connectToRandom() {
    if (username == null || username === '') {
        alert("Please login");
    } else {
        $.ajax({
            url: "/game/connect/random",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "player": username
            }),
            beforeSend: function(xhr) {
                // Add the CSRF token to the request header
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                gameId = data.gameId;
                playerType = 'O';
                reset();
                connectToSocket(gameId);
                alert("Congrats you're playing with: " + data.firstPlayer + "gameID: " + gameId);
                displayResponse(data);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}

function connectToSpecificGame() {
    if (username == null || username === '') {
        alert("Please login");
    } else {
        let localgameId = document.getElementById("game_id").value;
        if (localgameId == null || localgameId === '') {
            alert("Please enter game id");
        }
        $.ajax({
            url: "/game/connect",
            type: 'POST',
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify({
                "player": username,
                "gameId": localgameId
            }),
            beforeSend: function(xhr) {
                // Add the CSRF token to the request header
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                gameId = data.gameId;
                playerType = 'O';
                reset();
                connectToSocket(gameId);
                alert("Congrats you're playing with: " + data.firstPlayer + "gameID: " + gameId);
                displayResponse(data);
            },
            error: function (error) {
                console.log(error);
            }
        })
    }
}
