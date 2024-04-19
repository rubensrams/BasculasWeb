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

const formatCurrency = (num, decimals) => num.toLocaleString('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
});

function loadData(data, page) {
    $("#bodyTable").html("");
    for (i = 0; i < data.compras.length; i++) {
        let table = '<tr>' +
            '<td>' + data.compras[i].folio + '</td>' +
            '<td>' + data.compras[i].fecha + '</td>' +
            '<td>' + data.compras[i].usuario + '</td>' +
            '<td>' + data.compras[i].recuperador + '</td>' +
            '<td>' + data.compras[i].sucursal + '</td>' +
            '<td>' + data.compras[i].flete + '</td>' +
            '<td>' + data.compras[i].material + '</td>' +
            '<td>' + data.compras[i].pesoNeto + '</td>' +
            '<td>' + data.compras[i].precio + '</td>' +
            '<td style="text-align:right;">$' + formatCurrency(data.compras[i].total) + '</td>' +
            '<td>' + data.compras[i].status + '</td>' +
            '<td> <a href="' + $("#path").val() + 'catalogos/compra/update/' + data.compras[i].idCompra + '" class="btn btn-sm btn-icon item-edit">' +
            '      <i class="bx bxs-edit"></i>' +
            '   </a></td>' +
            '</tr>'
        $("#bodyTable").append(table);
    }
    loadPagination(data.count, page);
    loadSummary(data.count, page);
}

function callData(thisPage) {

    let fechaInicial = $("#fechaIni").val();
    if (fechaInicial == "") {
        fechaInicial = "2000-01-01T00:00:00.000-06:00";
    } else {
        fechaInicial = $("#fechaIni").val() + "T00:00:00.000-06:00";
    }

    let fechaFinal = $("#fechaFin").val();
    if (fechaFinal == "") {
        fechaFinal = "2030-01-01T00:00:00.000-06:00";
    } else {
        fechaFinal = $("#fechaFin").val() + "T23:59:59.000-06:00";
    }
    actualPage = thisPage;
    $.post($("#path").val() + "catalogos/compra/get", { rows: $("#numberRows option").filter(':selected').val(), page: actualPage, order: "NO_ORDER", fechaIni: fechaInicial, fechaFin: fechaFinal, idSucursal: $("#sucursal").val(), searchUser: $("#abcsSearch").val() })
        .done(function (data) {
            loadData(data, actualPage);
        });
}


callData(0);

$("#exportReport").on("click", function (e) {
    e.preventDefault();
    let param = '?sucursal=' + $("#sucursal").val() + '&fechaInicio=' + $("#fechaIni").val() + '&fechaFin=' + $("#fechaFin").val() + '&nombre=' + $("#abcsSearch").val();
    window.location.href = $(this).attr("href")+ param;

});