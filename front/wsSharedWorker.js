const wsocket = new WebSocket("ws://localhost:8000");

wsocket.onopen = onopen;
wsocket.onmessage = onmessageWS;
wsocket.onclose = onclose;

const ports = [];

const buffer = {
    messageId : 0,
    message: ""
}

onconnect = (e) => {
    const port = e.ports[0];
    ports.push(port);

    port.addEventListener("message", (e) => {
        onmessageUser(e, port);
    });

    port.start();// Required when using addEventListener. Otherwise called implicitly by onmessage setter.
};

function onmessageUser(e, port) {
    wsocket.send(e.data);
}

function onopen() {
    console.log("Connected!");
}

function onmessageWS(event) {
    console.log(event.data);
    try{
        let x = event.data;

        const i = x.lastIndexOf("}") + 1;
        x = x.substring(0, i);

        const msg = JSON.parse(x);
        if (msg.datatype === "state") {
            if(buffer.id != msg.id) {
                let m = buffer.message;

                setTimeout(() => {
                    m = JSON.stringify({
                        datatype: "state",
                        data: m
                    })

                    for(const port of ports) {
                        port.postMessage(m);
                    }
                })

                buffer.id = msg.id;
                buffer.message = "";
            }
            buffer.message += msg.data;

            return
        }
        if (msg.datatype === "init") {
            for(const port of ports) {
                port.postMessage(x);
            }
            return
        }

        console.warn("unsupported datatype", msg.datatype)
    } catch (e) {
        console.error(e);
        console.log(event.data);
    }
}

function onclose(e) {
    console.log("Connection closed.");
}
