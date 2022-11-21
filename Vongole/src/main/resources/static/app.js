var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        console.log('Connected to : ' + '/topic/greetings/'+$("#userId").val());
        stompClient.subscribe('/topic/greetings/'+$("#userId").val(), function (greeting) {       // 수신주소 /topic/greetings/{id}
            showGreeting("room: "+JSON.parse(greeting.body).chatRoomId+ " - [" + JSON.parse(greeting.body).userName+"] : " + JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello/"+$("#userId").val(), {}, JSON.stringify({'action': 'MESSAGE','userName': $("#name").val(),'chatRoomId':  $("#chatRoomId").val(),'content': $("#content").val()})); // 송신주소 /app/hello/{id}
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

