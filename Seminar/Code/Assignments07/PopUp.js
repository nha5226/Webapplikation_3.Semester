function openRed(){

    const theImage = new Image();
    theImage.src = "Red.jpg";

    if(theImage.complete){
        open()
    } else {
        theImage.addEventListener("load", start)
        function start() {
            theImage.removeEventListener("load", start)
            open()
        }

    }

    function open(){
        const winWidth = theImage.width + 20;
        const winHeight = theImage.height + 20;
        let myWindow = window.open("Red.jpg", null, 'height=' + winHeight.toString() + ', width=' + winWidth.toString() + ', toolbar=0, location=0, status=0, scrollbars=0, resizable=0');
        myWindow.document.write(" <button onclick='window.close()'>Close</button> <img src='Red.jpg' alt='Red'>")
    }
}

function openBlue(){

    const theImage = new Image();
    theImage.src = "Blue.jpg";

    if(theImage.complete){
        open()
    } else {
        theImage.addEventListener("load", start)
        function start() {
            theImage.removeEventListener("load", start)
            open()
        }

    }

    function open(){
        const winWidth = theImage.width + 20;
        const winHeight = theImage.height + 20;
        let myWindow = window.open("Blue.jpg", null, 'height=' + winHeight.toString() + ', width=' + winWidth.toString() + ', toolbar=0, location=0, status=0, scrollbars=0, resizable=0');
        myWindow.document.write(" <button onclick='window.close()'>Close</button> <img src='Blue.jpg' alt='Red'>")
    }
}