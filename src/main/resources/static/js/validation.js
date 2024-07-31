document.addEventListener("DOMContentLoaded", function() {
    var formulaire = document.getElementById("form-creer"); // formulaire
    formulaire.addEventListener("submit", function(e) {
        var motDePasse = document.getElementById("motDePasse").value; // champ de mot de passe
        var confirmation = document.getElementById("confirmMDP").value; // champ de confirmation
        var messageErreur = document.getElementById("p-erreur"); //paragraphe pour le message d'erreur

		console.log("mot de passe : " + motDePasse + " confirmation : " + confirmation);

         // Efface le message d'erreur précédent
        messageErreur.textContent = "";

        if(motDePasse !== confirmation) {
            e.preventDefault(); // Empêche la soumission du formulaire
            
             // Ajoute le message d'erreur
            messageErreur.textContent = "Les mots de passe ne correspondent pas.";
            messageErreur.style.color = "#ff6a6a";
        }
    });
});
