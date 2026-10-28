# Projet MVC

Le classement est saisi manuellement : le rang `1` correspond au meilleur athlète. Dans la liste, chaque athlète reste affiché et son rang peut être modifié directement ; les athlètes non classés apparaissent à la fin.

Pour mettre à jour une base déjà créée, exécuter les migrations présentes dans `src/main/java/sio/la2028/database/la2028.sql` :

```sql
ALTER TABLE athlete ADD COLUMN IF NOT EXISTS prenom VARCHAR(50) DEFAULT NULL;
ALTER TABLE athlete ADD COLUMN IF NOT EXISTS date_naissance DATE DEFAULT NULL;
ALTER TABLE athlete ADD COLUMN IF NOT EXISTS rang INT DEFAULT NULL;
```
