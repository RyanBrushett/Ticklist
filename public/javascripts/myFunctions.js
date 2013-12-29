function updateactive(){
    var path = window.location.pathname;
    if (path == "/"){
        document.getElementById("dashboard").className="active";
    }
    if (path == "/biglist"){
        document.getElementById("biglist").className="active";
    }
    if (path == "/about"){
        document.getElementById("aboutpage").className="active";
    }
    if (path == "/crags"){
        document.getElementById("locations").className="active";
    }
}
