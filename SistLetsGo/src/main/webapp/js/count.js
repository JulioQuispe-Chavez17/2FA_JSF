let timer, currSeconds = 0;

function resetTimer() {

    timeParagraph = document.getElementById("secs");
    timeParagraph.innerHTML = "";

    clearInterval(timer);

    currSeconds = 30;

    timer =
        setInterval(startIdleTimer, 1000);
}
window.onload = resetTimer;


function startIdleTimer() {


    valido = document.getElementById("secs");
    if(currSeconds <= 6 && currSeconds > 0){
        valido.innerText =(currSeconds);
    }else{
        valido.innerText = 0;
    }




    currSeconds--;


}