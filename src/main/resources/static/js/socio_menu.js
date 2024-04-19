
function loadPagination(count, page) {
    $('#paginator li:not(:first-child):not(:last-child)').remove();
    let rows = $("#numberRows option").filter(':selected').val();
    let totalPages = count % rows == 0 ? count / rows : Math.trunc(count / rows) + 1;

    if (totalPages < 8) {
        for (i = 0; i < totalPages; i++) {
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == i ? "active" : "") + "'><button class='page-link' data-id='" + (i + 1) + "'>" + (i + 1) + "</button></li>");
        }
    }
    if (totalPages >= 8) {
        $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == 0 ? "active" : "") + "'><button class='page-link' data-id='1'>1</button></li>");
        if (page <= 4) {
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == 1 ? "active" : "") + "'><button class='page-link' data-id='" + 2 + "'>" + 2 + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == 2 ? "active" : "") + "'><button class='page-link' data-id='" + 3 + "'>" + 3 + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == 3 ? "active" : "") + "'><button class='page-link' data-id='" + 4 + "'>" + 4 + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == 4 ? "active" : "") + "'><button class='page-link' data-id='" + 5 + "'>" + 5 + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item disabled'><button class='page-link'>...</button></li>");
        }
        else if (page > 4 && page <= (totalPages - 4)) {
            $("#paginator li:last-child").before("<li class='paginate_button page-item disabled'><button class='page-link'>...</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item'><button class='page-link' data-id='" + page + "'>" + page + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item active'><button class='page-link' data-id='" + (page + 1) + "'>" + (page + 1) + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item'><button class='page-link' data-id='" + (page + 2) + "'>" + (page + 2) + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item disabled'><button class='page-link'>...</button></li>");
        }
        else {
            $("#paginator li:last-child").before("<li class='paginate_button page-item disabled'><button class='page-link'>...</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == (totalPages - 5) ? "active" : "") + "'><button class='page-link' data-id='" + (totalPages - 4) + "'>" + (totalPages - 4) + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == (totalPages - 4) ? "active" : "") + "'><button class='page-link' data-id='" + (totalPages - 3) + "'>" + (totalPages - 3) + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == (totalPages - 3) ? "active" : "") + "'><button class='page-link' data-id='" + (totalPages - 2) + "'>" + (totalPages - 2) + "</button></li>");
            $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == (totalPages - 2) ? "active" : "") + "'><button class='page-link' data-id='" + (totalPages - 1) + "'>" + (totalPages - 1) + "</button></li>");
        }
        $("#paginator li:last-child").before("<li class='paginate_button page-item " + (page == (totalPages - 1) ? "active" : "") + "'><button class='page-link' data-id='" + totalPages + "'>" + totalPages + "</button></li>")
    }

    if (page == 0) {
        $(".paginate_button.previous").addClass("disabled")
    } else {
        $(".paginate_button.previous").removeClass("disabled")
    }

    if (page == totalPages - 1) {
        $(".paginate_button.next").addClass("disabled")
    } else {
        $(".paginate_button.next").removeClass("disabled")
    }
    eventLinks();
}

function loadSummary(total, page) {
    let rows = $("#numberRows option").filter(':selected').val();
    if (total > 0) {
        $("#labelRecords").text("Mostrando " + (page * rows + 1) + " a " + (((page + 1) * rows) >= total ? total : (page + 1) * rows) + " de " + total + " registros");
    }
}

function loadData(data, page) {
    $("#bodyTable").html("");
    for (i = 0; i < data.recuperadores.length; i++) {
        let table = '<tr>' +
            '<td>' + data.recuperadores[i].nombre + ' ' + data.recuperadores[i].apPaterno + ' ' +  data.recuperadores[i].apMaterno + '</td>' +
            '<td>' + data.recuperadores[i].telefono + '</td>' +
            '<td> <a href='+$("#path").val()+'catalogos/socio/update/' + data.recuperadores[i].idRecuperador + ' class="btn btn-sm btn-icon item-edit">' +
            '      <i class="bx bxs-edit"></i>' +
            '   </a></td>' +
            '</tr>'
        $("#bodyTable").append(table);
    }
    loadPagination(data.count, page);
    loadSummary(data.count, page);
}

function eventLinks() {
    $(".page-link:not(.nondigit)").on("click", function () {
      callData($(this).attr("data-id") - 1);
    });
  }
  
  $(".nondigit").on("click", function () {
    callData($(this).data("direction") == "next" ? actualPage + 1 : actualPage - 1);
  });
  
  $("#searchBtn").on("click", function (e) {
    callData(0);
  });
  
  $("#numberRows").on('change', function () {
    callData(0);
  });

function callData(thisPage) {
    actualPage = thisPage;
    $.post($("#path").val()+"catalogos/socio/get", { rows: $("#numberRows option").filter(':selected').val(), page: actualPage, order: "NO_ORDER", search: $("#searchUser").val() })
        .done(function (data) {
            loadData(data, actualPage);
        });
}


callData(0);