function createDeleteLink(game) {
    var result = "<td>";

    if (game && game.gameStatus === EMPTY_GAME && game.creator.username === $("#usernameNav").text()){
        result += "<a href='' style='color: Red' onclick='return false;' class=deleteLink>X</a>";
    }
    else {
        result +="-";
    }
    result += "</td>";

    return result;
}

function refreshGamesList(games) {
    //clear all current users
    var tableGame = $("#table_games");
    tableGame.find("tr:gt(0)").remove(); // remove everything not in header of table

    var rows = "";

    //add rows to table
    $.each(games || [], function (index, game) {
        //console.log("Adding game #" + index + ": " + game.gameName);

        rows +=
                "<tr>"+ createDeleteLink(game) +
                "<td>" + game.gameName + "</td>" +
                "<td>" + game.creator.username + "</td>" +
                "<td>" + game.boardSize + "</td>" +
                "<td>" + translateGameStatus(game.gameStatus) + "</td>" +
                "<td>" + createJoinGameLink(game) + "</td></tr>";

        tableGame.on('click', '.joinLink', function () {
            joinGame(game);
        });

        tableGame.on('click', '.watchLink', function () {
            watchGame(game);
        });

        tableGame.on('click', '.deleteLink', function() {
            deleteGame(game);
        });

    });


    $(rows).appendTo("#table_games tbody");
}



function stopListsRefresh() {
    if (intervalRefreshLists !== null) {
        clearInterval(intervalRefreshLists);
    }
}

function refreshUsersList(users) {
    //clear all current users
    $("#table_users").find("tr:gt(0)").remove();


    // rebuild the list of users: scan all users and add them to the list of users
    var rows = "";
    $.each(users || [], function (index, username) {
        //console.log("Adding user #" + index + ": " + username);
        rows += "<tr><td>" + username + "</td></tr>";
    });

    $(rows).appendTo("#table_users tbody");
}


function translateGameStatus(gameStatus) {
    switch (gameStatus) {
        case ONE_PLAYER:
            return "One player waiting";
            break;
        case EMPTY_GAME:
            return "Empty";
            break;
        case FULL_GAME:
            return "Game is full";
            break;
    }

    return "";
}

function createJoinGameLink(game) {
    var result = "";
    switch (game.gameStatus) {
        case EMPTY_GAME:
        case ONE_PLAYER:
            result += "<a href='' onclick='return false;' class='joinLink'>"
                + "Play" + "</a>";
            break;
        case FULL_GAME:
            result += "<a href='' onclick='return false;' class='watchLink'>"
                + "Watch" + "</a>";
            break;
        default:
            break;
    }

    //console.log("In crate game result is" + result);
    return result;
}