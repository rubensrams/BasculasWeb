
$("#update").on("click", function (e) {
  $("#success").addClass("d-none");
    if (validForm()) {
      $.post($("#path").val()+"catalogos/socio/update", $("#socioform").serialize())
        .done(function (data) {
          if (data) {
            $("#success").removeClass("d-none");
            //window.location.href = $("#path").val()+"catalogos/socio/";
          } else {
            alert("ocurrio un error")
          }
          e.preventDefault();
        });
    }
    e.preventDefault();
  });
  
  
  function validForm() {
    let resultado = true;
  
    if ($("#nombre").val().length < 3) {
      $("#nombre").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#nombre").removeClass("invalid-data").next().removeClass("invalid-data-labl");
    }
  
    if (!$("#apPaterno").val()) {
      $("#apPaterno").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#apPaterno").removeClass("invalid-data").next().removeClass("invalid-data-labl");
    }
  
    if (!$("#apMaterno").val()) {
      $("#apMaterno").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#apMaterno").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }
  
    if (!(/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/).test($("#email").val())) {
      $("#email").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#email").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }
      
    if (!$("#telefono").val()) {
      $("#telefono").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#telefono").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }
  
    return resultado;
  }
  