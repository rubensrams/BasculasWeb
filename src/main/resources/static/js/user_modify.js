var actualPage;

$('#image').on("change", function () {
  var val = $(this).val(),
    btn = $('#uploadPhoto');
  val ? btn.removeAttr("disabled") : btn.attr("disabled");
});

$("#update").on("click", function (e) {
  $("#success").addClass("d-none");
  if (validForm()) {
    $.post($("#path").val() + "catalogos/usuario/update", $("#userform").serialize())
      .done(function (data) {
        if (data) {
          $("#success").removeClass("d-none");
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

  if ($("#username").val().length < 3) {
    $("#username").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  }
  else {
    $("#username").removeClass("invalid-data").next().removeClass("invalid-data-labl");
  }

  if (!$("#name").val()) {
    $("#name").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  }
  else {
    $("#name").removeClass("invalid-data").next().removeClass("invalid-data-labl");
  }

  if (!$("#lastName").val()) {
    $("#lastName").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  }
  else {
    $("#lastName").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }

  if (!(/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/).test($("#email").val())) {
    $("#email").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  }
  else {
    $("#email").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }

  if ($("#rfc").val().trim() != 0) {

    if (!(/^([A-ZÑ&]{3,4}) ?(?:- ?)?(\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\d|3[01])) ?(?:- ?)?([A-Z\d]{2})([A\d])$/).test($("#rfc").val())) {
      $("#rfc").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    } else {
      $("#rfc").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }
  }
  else {
    $("#rfc").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }

  if ($("#rol").val() == 0) {
    $("#rol").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  } else {
    $("#rol").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }
  /*
    if (!$("#address").val()) {
      $("#address").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#address").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }
  
    if (!$("#city").val()) {
      $("#city").addClass("invalid-data").next().addClass("invalid-data-labl");
      resultado = false;
    }
    else {
      $("#city").removeClass("invalid-data").next().removeClass("invalid-data-labl")
    }*/

  if (!$("#state").val()) {
    $("#state").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  }
  else {
    $("#state").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }

  if ($("#sucursal").val() == 0) {
    $("#sucursal").addClass("invalid-data").next().addClass("invalid-data-labl");
    resultado = false;
  } else {
    $("#sucursal").removeClass("invalid-data").next().removeClass("invalid-data-labl")
  }

  return resultado;
}
