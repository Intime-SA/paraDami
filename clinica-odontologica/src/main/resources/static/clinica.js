document.addEventListener("DOMContentLoaded", function () {
  // Obtener referencia al formulario
  const form = document.getElementById("contactForm");

  // Verificar si el formulario existe antes de agregar el evento
  if (form) {
    // Agregar un evento al envío del formulario
    form.addEventListener("submit", function (event) {
      event.preventDefault(); // Evitar que el formulario se envíe automáticamente

      // Obtener los valores de los campos del formulario
      const matricula = document.getElementById("matricula").value;
      const nombre = document.getElementById("name").value;
      const apellido = document.getElementById("apellido").value;

      // Imprimir los valores en la consola
      console.log("Matrícula:", matricula);
      console.log("Nombre:", nombre);
      console.log("Apellido:", apellido);

      // Aquí puedes enviar los datos a un servidor utilizando AJAX u otras técnicas
    });
  }
});
