document.addEventListener("DOMContentLoaded", function() {
    const radioAchats = document.getElementById("achats");
    const radioVentes = document.getElementById("ventes");

    const achatsDropdown = document.getElementById("achats-dropdown");
    const ventesDropdown = document.getElementById("ventes-dropdown");

    // État initial
    if (radioAchats.checked) {
        ventesDropdown.disabled = true;
    } else if (radioVentes.checked) {
        achatsDropdown.disabled = true;
    }

    radioAchats.addEventListener("change", function() {
        if (this.checked) {
            ventesDropdown.disabled = true;
            achatsDropdown.disabled = false;
        }
    });

    radioVentes.addEventListener("change", function() {
        if (this.checked) {
            achatsDropdown.disabled = true;
            ventesDropdown.disabled = false;
        }
    });
});
