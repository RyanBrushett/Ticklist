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
        addCragInit();
    }
    if (path == "/addboulder"){
        document.getElementById("addBoulder").className="active";
    }
}

Element.prototype.remove = function() {
    this.parentElement.removeChild(this);
}

NodeList.prototype.remove = HTMLCollection.prototype.remove = function() {
    for(var i = 0, len = this.length; i < len; i++) {
        if(this[i] && this[i].parentElement) {
            this[i].parentElement.removeChild(this[i]);
        }
    }
}

function addCragInit(){
    var add_but = document.querySelector("#newCragButton");
    var cname = document.querySelector("#cragName");
    var locat = document.querySelector("#location");
    var tbody = document.querySelector("#cragsTableBody");
    if (add_but == null) return;
    
    function clear_fields(){
        cname.value = "";
        locat.value = "";
    }

    add_but.addEventListener('click',function(evt){
        var rec = {
            cragName : cname.value,
            location : locat.value
        };
        var json = JSON.stringify(rec);
        var r = jsRoutes.controllers.Crags.addCrag();
        local_ajax_mod.ajax_request({
            method: "POST",
            link: r.url,
            mime: 'application/json',
            doc: json,
            ok_fn: function(req) {
                try {
                    var tr = document.createElement("tr");
                    tr.setAttribute("id",cname.value);
                    tr.innerHTML = "<td>" +
                    "<a href=\"@routes.Boulders.filteredList(" + cname.value + ")\">" +
                    "<h5>" + cname.value + "</h5></a></td><td><h5>" +
                    locat.value + "</h5></td><td><button id=\"deleteCragButton\" class=\"btn btn-danger\" " +
                    "onclick=\"deleteCrag('" + cname.value + "')\">Delete</button></td>";
                    tbody.appendChild(tr);
                    clear_fields();
                }
                catch(e){
                    console.log(e);
                }
            },
            err_fn: function(req) {
                window.alert("failed: " + req);
            }
        }, false);
    }); 
}

function deleteCrag(name){
    var rec = {
        cragName : name
    };
    var json = JSON.stringify(rec);
    var r = jsRoutes.controllers.Crags.deleteCrag(name);
    local_ajax_mod.ajax_request({
        method: "DELETE",
        link: r.url,
        mime: 'application/json',
        doc: json,
        ok_fn: function(req){
            var tr = document.getElementById(name).remove();
        },
        err_fn: function(req){
            window.alert("Failed: " + req);
        }
    }, false);
}
