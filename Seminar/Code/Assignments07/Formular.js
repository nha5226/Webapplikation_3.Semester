
function Validierung(){
    const Vorname = document.getElementById('Vorname');
    const Nachname = document.getElementById('Nachname');

    const Weiblich = document.getElementById('Weiblich');
    const Männlich = document.getElementById('Männlich');
    const Divers = document.getElementById('Divers');

    const Adresse = document.getElementById('Adresse');

    const Username = document.getElementById('Username');

    const Passwort = document.getElementById('psw');
    const Pswbest = document.getElementById('pswbest');

     const Buch = document.getElementById('Buch');
     const DVD = document.getElementById('DVD');
     const CD = document.getElementById('CD')

    const KundIn = document.getElementById('KundIn');
    const MitarbeiterIn = document.getElementById('MitarbeiterIn');
    const Admin = document.getElementById('Admin')

    const Mail = document.getElementById('Mail');

    Vorname_val(Vorname);
    Nachname_val(Nachname);
    Geschlecht_val(Weiblich, Männlich, Divers);
    Adresse_val(Adresse);
    Username_val(Username);
    Passwort_val(Passwort);
    Passwortbestaetigung_val(Passwort, Pswbest);
    Interessen_val(Buch, DVD, CD);
    Nutzerkategorien_val(KundIn, MitarbeiterIn, Admin);
    Mail_val(Mail);
}

function Vorname_val(Vorname) {
    const buchstaben = /[a-zA-Z]/;
    if (Vorname.value.match(buchstaben)) {
        return true;
    } else {
        alert("Bitte geben Sie nur Buchstaben ein");
        Vorname.focus();
        return false;
    }
}

function Nachname_val(Nachname) {
    const buchstaben = /[a-zA-Z]/;
    if (Nachname.value.match(buchstaben)) {
        return true;
    } else {
        alert("Bitte geben Sie nur Buchstaben ein");
        Nachname.focus();
        return false;
    }
}

function Geschlecht_val(Weiblich, Männlich, Divers) {

    if (!Weiblich.checked && !Männlich.checked && !Divers.checked) {
        alert("Bitte wählen Sie ein Geschlecht");
        Divers.focus();
        return false;
    } else {
        return true;
    }
}

function Adresse_val(Adresse) {

    if (Adresse.value == "") {
        alert("Bitte geben Sie eine Adresse ein");
        Adresse.focus();
        return false;
    } else {
        return true;
    }
}


function Username_val(Username) {

    if (Username.value == "") {
        alert("Bitte geben Sie einen Username ein");
        Username.focus();
        return false;
    }

    if (Username.value.length <= 5){
        alert("Der Username muss mindestens 5 Zeichen lang sein");
        Username.focus();
        return false;
    }

    if (Username.value.length >= 12){
        alert("Der Username darf maximal nur 12 Zeichen lang sein");
        Username.focus();
        return false;
    }

    if (Username.value.match("_") && !Username.value.match(/^[A-Za-z\s]+$/)){
        alert("Im Username ist ein ungültiges Zeichen");
        Username.focus();
        return false;
    }
    if (Username.value.match(" ")){
        alert("Im Username ist ein Leerzeichen");
        Username.focus();
        return false;
    }
    return true;
}



function Passwort_val(Passwort) {

    if (Passwort.value == "") {
        alert("Bitte geben Sie einen Passwort ein");
        Passwort.focus();
        return false;
    }

    if (Passwort.value.length <= 6){
        alert("Das Passwort muss mindestens 6 Zeichen lang sein");
        Passwort.focus();
        return false;
    }

    if (Passwort.value.length >= 11){
        alert("Das Passwort darf maximal nur 11 Zeichen lang sein");
        Passwort.focus();
        return false;
    }

    if (!Passwort.value.match(/^[A-Za-z\s]+$/)){
        alert("Im Passwort ist ein ungültiges Zeichen");
        Passwort.focus();
        return false;
    }
    if (Passwort.value.match(" ")){
        alert("Im Passwort ist ein Leerzeichen");
        Passwort.focus();
        return false;
    }
    return true;
}

function Passwortbestaetigung_val(Passwort, Pswbest){
    if (!Passwort.value.match(Pswbest.value)){
        alert("Passwort stimmt nicht überein");
        Passwort.focus();
        Pswbest.focus();
    }
    return true;
}


function Interessen_val(Buch, DVD, CD){

    if (Buch.checked == false && DVD.checked == false && CD.checked == false) {
        alert("Bitte wählen Sie eine Interesse aus");
        Buch.focus();
        DVD.focus();
        CD.focus();
        return false;
    }

    return true;
}

function Nutzerkategorien_val(KundIn, MitarbeiterIn, Admin){

    if (KundIn.checked == false && MitarbeiterIn.checked == false && Admin.checked == false) {
        alert("Bitte wählen Sie eine Nutzerkategorie aus");
        KundIn.focus();
        MitarbeiterIn.focus();
        Admin.focus();
        return false;
    }

    return true;
}


function Mail_val(Mail){

    const validRegex = /^[a-zA-Z]([a-zA-Z_-][.]{0,1})*@[a-z0-9]([a-z0-9_-][.]{0,1}){2,62}[^.-]$/;

    if (!Mail.value.match(validRegex)){
        alert("Email Adresse ungültig. Bitte versuchen Sie es erneut");
        Mail.focus();
        return false;
    } else {


        return true;
    }
}
