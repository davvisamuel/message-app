'use strict';

const url = "ws://localhost:8080/ws";
const client = new StompJs.Client({
    brokerURL: url
})

let buttonConnect;
let buttonDisconnect;
let buttonSendMessages;
let buttonClearMessages;
let messages;
let token;
let username;
let message;

window.addEventListener("load", () => {
    buttonConnect = document.getElementById("connect");
    buttonDisconnect = document.getElementById("disconnect");
    buttonSendMessages = document.getElementById("sendMessage");
    buttonClearMessages = document.getElementById("clearMessages");
    messages = document.getElementById("messages");

    token = document.getElementById("token");
    username = document.getElementById("username");
    message = document.getElementById("message");

    buttonConnect.disabled = false;
    buttonDisconnect.disabled = true;
})

client.onConnect = () => {
    client.subscribe('/user/queue/private-message', (message) => {
        printMessage(messages, message.body);
    })
}

client.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
}

client.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
}

function connect() {
    if (!token.value) {
        alert("Digite o token antes de conectar");
        return;
    }

    client.connectHeaders = {
        Authorization: "Bearer " + token.value
    }

    client.activate();
    buttonConnect.disabled = true;
    buttonDisconnect.disabled = false;
    buttonSendMessages.disabled = false;
    clearMessage(messages);
}

function disconnect() {
    client.deactivate();
    buttonConnect.disabled = false;
    buttonDisconnect.disabled = true;
    buttonSendMessages.disabled = true;
    clearMessage(messages);
}

function sendMessage() {
    const payload = {
        username: username.value,
        message: message.value
    };

    client.publish({
        destination: "/app/private-message",
        body: JSON.stringify(payload)
    });
}

function printMessage(textId, data) {
    textId.innerHTML += data + "<br/>";
}

function clearMessage(textId) {
    textId.innerHTML = "";
}