
$("#SavePass").on("click", function () {
    if ($("#changePass").val().trim().length < 6) {
      $("#passInvalid").addClass("invalid-data-labl");
      $("#passInvalid").text("El password debe ser de almenos 6 caracteres");
      return;
    }
    if ($("#changePass").val() == $("#repeatPass").val()) {
      $("#passInvalid").removeClass("invalid-data-labl");
      $.post($("#path").val()+"catalogos/usuario/password", { password: $("#changePass").val(), id: $("#currentId").val() })
        .done(function (data) {
          $('#modalCenter').modal('hide');
        });
    } else {
      $("#passInvalid").addClass("invalid-data-labl");
      $("#passInvalid").text("El password no coincide");
    }
  });
  
  function loadUsers(data, page) {
    $("#bodyUsers").html("");
    for (i = 0; i < data.users.length; i++) {
      let dataUser = '<tr>' +
        '                       <td>' +
        '                         <div class="d-flex justify-content-start align-items-center">' +
        '                           <div class="d-none d-sm-block">' +
        '                             <div class="avatar me-2">' +
        '                               <span class="avatar-initial rounded-circle ' + data.users[i].color +
        '                                 ">' + data.users[i].name.charAt(0) + data.users[i].lastName.charAt(0) + '</span>' +
        '                             </div>' +
        '                           </div>' +
        '                           <div class="d-flex flex-column">' +
        '                             <span class="emp_name text-truncate">' + data.users[i].name + ' ' + data.users[i].lastName + '</span>' +
        '                             <small class="emp_post text-truncate text-muted">' + data.users[i].rol + '</small>' +
        '                           </div>' +
        '                         </div>' +
        '                       </td>' +
        '                       <td class="d-none d-md-table-cell">' + data.users[i].username + '</td>' +
        '                       <td class="d-none d-md-table-cell">'+
        '                         <span class="badge ' + (data.users[i].active == 'abierto' ? 'bg-label-success' : 'bg-label-warning') + '">' +
        '                           ' + (data.users[i].active == 'abierto' ? 'Activo' : 'Inactivo') + '</span>' +'</td>' +
        '                       <td class="d-none d-md-table-cell">' + data.users[i].sucursal + '</td>' +
        '                       <td class="d-none d-md-table-cell">' +
        '                           ' + (data.users[i].enabled ? 'Habilitado' : 'Deshabilitado') +
        '                       </td>' +
        '                       <td>' +
        '                         <button class="btn btn-sm btn-icon item-edit clickbtn" data-id="' + data.users[i].id + '"' +
        '                           data-bs-toggle="modal" data-bs-target="#modalCenter">' +
        '                           <i class="bx bxs-lock"></i>' +
        '                         </button>' +
        '                         <a href="'+$("#path").val()+'catalogos/usuario/update/' + data.users[i].id + '" class="btn btn-sm btn-icon item-edit">' +
        '                           <i class="bx bxs-edit"></i>' +
        '                         </a>' +
        '                       </td>' +
        '                     </tr>'
      $("#bodyUsers").append(dataUser);
    }
    loadPagination(data.count, page);
    loadSummary(data.count, page);
    resetModal();
  }


function resetModal() {
    $(".clickbtn").on("click", function () {
      $("#changePass").val("");
      $("#repeatPass").val("");
      $("#currentId").val($(this).attr("data-id"));
      $("#passInvalid").removeClass("invalid-data-labl");
      $("#passInvalid").text("");
    });
  }
  
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

    //call users for datatable
    function callData(thisPage) {
      actualPage = thisPage;
      $.post($("#path").val()+"catalogos/usuario/get", { rows: $("#numberRows option").filter(':selected').val(), page: actualPage, order: "NO_ORDER", idUser: $("#searchXSD").val(),conectado: $("#conectado").val()})
        .done(function (data) {
          loadUsers(data, actualPage);
        });
    }
  
  //if(NoobtenerRegistros == false) //como arreglar esto
    callData(0);
  
  eventLinks();
  resetModal();