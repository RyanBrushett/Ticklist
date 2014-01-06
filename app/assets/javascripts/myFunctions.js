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
        addBoulderInit();
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

function addBoulderInit(){
    var add_but = document.querySelector("#newBoulderButton");
    var climbname = document.querySelector("#climbName");
    var fa = document.querySelector("#firstAssent");
    var g = document.querySelector("#grade");
    var cragname = document.querySelector("#cragName");

    if (add_but == null) return;

    function clear_fields(){
        climbname.value = "";
        fa.value = "";
        g.value = "";
    }

    add_but.addEventListener('click',function(evt){
        var rec = {
            climbName : climbname.value,
            firstAssent : fa.value,
            grade : g.value,
            cragName : cragname.value
        };
        var json = JSON.stringify(rec);
        var r = jsRoutes.controllers.Boulders.addBoulder();
        local_ajax_mod.ajax_request({
            method: "POST",
            link: r.url,
            mime: 'application/json',
            doc: json,
            ok_fn: function(req){
                var added = document.getElementById("beenAdded");
                added.innerHTML = "<h5>Added!</h5>";
            },
            err_fn: function(req){
                window.alert("A boulder by this name already exists: " + climbname.value);
            }
        }, false);
    });
}

function deleteBoulder(name){
    var rec = {
        climbName : name
    };
    var json = JSON.stringify(rec);
    var r = jsRoutes.controllers.Boulders.deleteBoulder(name);
    local_ajax_mod.ajax_request({
        method: "DELETE",
        link: r.url,
        mime: 'application/json',
        doc: json,
        ok_fn: function(req){
            var tr = document.getElementById(name).remove();
        },
        err_fn: function(req){
            window.alert("It Failed!");
        }
    }, false);
}

function tickBoulder(name){
    var rec = {
        climbName : name
    };
    var json = JSON.stringify(rec);
    var r = jsRoutes.controllers.Boulders.tickBoulder(name);
    local_ajax_mod.ajax_request({
        method: "POST",
        link: r.url,
        mime: 'application/json',
        doc: json,
        ok_fn: function(req){
            var td = document.getElementById("buttons" + name);
            td.innerHTML = "";
            td.innerHTML = "<div class=\"btn-group btn-group-sm\">" +
            "<button type=\"button\" class=\"btn\">Untick</button>" +
            "<button type=\"button\" class=\"btn\ btn-danger\" onclick=\"" +
            "deleteBoulder('" + name + "')\">Delete</button></div>";
        },
        err_fn: function(req){
            window.alert("it failed");
        }
    }, false);
}
