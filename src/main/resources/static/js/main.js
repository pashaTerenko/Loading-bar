function GenerateTable(data) {
    //Build an array containing Customer records.
    var customers = new Array();
    customers.push(["URL", "Load progress", "Check"]);

    for (var i = 0; i < data.length; i++) {
        customers.push([data[i].urlF, data[i].procent, data[i].uuid]);
    }
    try {
        if (customers.length > 1) {

            for (var i = 1; i < customers.length; i++) {
                console.log(document.getElementById(customers[i][2]))
                if (document.getElementById(customers[i][2]).checked==true) {
                    checkedList.set(customers[i][2], true);

                    console.log(checkedList)
                } else {
                    checkedList.set(customers[i][2], false);
                    console.log(checkedList)
                }
            }
        }
    } catch (e) {
    }

    //Create a HTML Table element.
    var table = document.createElement("TABLE");
    table.className = "table table-striped";
    table.border = "1";

    //Get the count of columns.
    var columnCount = customers[0].length;


    //Add the header row.
    var row = table.insertRow(-1);
    for (var i = 0; i < columnCount; i++) {
        var headerCell = document.createElement("TH");
        headerCell.innerHTML = customers[0][i];
        row.appendChild(headerCell);
    }

    //Add the data rows.
    for (var i = 1; i < customers.length; i++) {
        row = table.insertRow(-1);
        for (var j = 0; j < columnCount; j++) {
            var cell = row.insertCell(-1);
            if (j != 2) {
                cell.innerHTML = customers[i][j];
            } else {

                cell.innerHTML = '<input type="checkbox" id="' + customers[i][2] + '" value="' + customers[i][2]+ '" >';


            }

        }
    }


    var dvTable = document.getElementById("dvTable");

    dvTable.innerHTML = "";
    dvTable.appendChild(table);
    for (var i = 1; i < customers.length; i++) {
        document.getElementById(customers[i][2]).checked = checkedList.get(customers[i][2]);
    }
}
var checkedList=new Map();
function assignButtons() {
    $("#submitButton").click(function (e) {

        if (($("#slugInput").val() != "") ) {
            $("#Help").text("");
            var slug=$("#slugInput").val()

            var xhr = new XMLHttpRequest();


            xhr.open('POST', 'load?URLs='+slug, false);
            xhr.send();
            if (xhr.status != 200) {
                alert( xhr.status + ': ' + xhr.statusText );
            } else {



            }


        }
    });
    $('#deleteButton').click(function() {
        var idList = { 'toDelete' : []};

        $(":checked").each(function() {
            idList['toDelete'].push($(this).val());
        });
        console.log( idList['toDelete']);
        $.post("/delete", idList, function(data, status) {

        });
    });
    $('#stopButton').click(function() {
        var idList = { 'toDelete' : []};

        $(":checked").each(function() {
            idList['toDelete'].push($(this).val());
        });
        console.log( idList['toDelete']);
        $.post("/stop", idList, function(data, status) {

        });
    });
    $('#resumeButton').click(function() {
        var idList = { 'toDelete' : []};

        $(":checked").each(function() {
            idList['toDelete'].push($(this).val());
        });
        console.log( idList['toDelete']);
        $.post("/resume", idList, function(data, status) {

        });
    });

}
function monitor() {

        $.getJSON('/monitor' , function(data) {
            GenerateTable(data);
        })
    setTimeout(arguments.callee, 1000);


}



$(document).ready(function(e) {



    assignButtons();
    monitor();




})