$("#update").on("click", function (e) {

      $.post($("#path").val()+"catalogos/compra/update", $("#compraform").serialize())
        .done(function (data) {
          if (data) {
            window.location.href = $("#path").val()+"catalogos/compra/";
          } else {
            alert("ocurrio un error")
          }
          e.preventDefault();
        });
    
    e.preventDefault();
  });