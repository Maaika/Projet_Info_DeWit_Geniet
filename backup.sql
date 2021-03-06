PGDMP     5    .                v           academie    10.3    10.3 $    -           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            .           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            /           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            0           1262    16393    academie    DATABASE     �   CREATE DATABASE academie WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_France.1252' LC_CTYPE = 'French_France.1252';
    DROP DATABASE academie;
             postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            1           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            2           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    49152    administrateur    TABLE     \   CREATE TABLE public.administrateur (
    login text NOT NULL,
    password text NOT NULL
);
 "   DROP TABLE public.administrateur;
       public         postgres    false    3            �            1259    32768    adresse    TABLE     p   CREATE TABLE public.adresse (
    id integer NOT NULL,
    adresse text NOT NULL,
    lat real,
    lon real
);
    DROP TABLE public.adresse;
       public         postgres    false    3            �            1259    24580    college    TABLE     �   CREATE TABLE public.college (
    id_adr integer NOT NULL,
    nom text NOT NULL,
    site_internet text,
    numero_academique integer NOT NULL
);
    DROP TABLE public.college;
       public         postgres    false    3            �            1259    16397    departement    TABLE     �   CREATE TABLE public.departement (
    id integer NOT NULL,
    id_college integer NOT NULL,
    id_responsable integer NOT NULL,
    nom text
);
    DROP TABLE public.departement;
       public         postgres    false    3            �            1259    32773 
   enseignant    TABLE     �  CREATE TABLE public.enseignant (
    id integer NOT NULL,
    prise_de_fonction date NOT NULL,
    id_college_principal integer NOT NULL,
    id_college_secondaire integer,
    id_adresse integer NOT NULL,
    id_dept_principal integer NOT NULL,
    id_dept_secondaire integer,
    id_matiere integer,
    nom text NOT NULL,
    prenom text NOT NULL,
    telephone text,
    mail text,
    login text,
    password text
);
    DROP TABLE public.enseignant;
       public         postgres    false    3            �            1259    32776    etudiant    TABLE     �   CREATE TABLE public.etudiant (
    id integer NOT NULL,
    annee_entree_college date NOT NULL,
    id_college integer NOT NULL,
    nom text NOT NULL,
    prenom text NOT NULL,
    mail text,
    telephone text,
    login text,
    password text
);
    DROP TABLE public.etudiant;
       public         postgres    false    3            �            1259    32779    matiere    TABLE     o   CREATE TABLE public.matiere (
    id integer NOT NULL,
    id_salle integer NOT NULL,
    nom text NOT NULL
);
    DROP TABLE public.matiere;
       public         postgres    false    3            �            1259    32808    matiere_etudiant    TABLE     Z   CREATE TABLE public.matiere_etudiant (
    id_matiere integer,
    id_etudiant integer
);
 $   DROP TABLE public.matiere_etudiant;
       public         postgres    false    3            �            1259    32785    note    TABLE     }   CREATE TABLE public.note (
    valeur integer NOT NULL,
    id_etudiant integer NOT NULL,
    id_matiere integer NOT NULL
);
    DROP TABLE public.note;
       public         postgres    false    3            �            1259    32782    salle    TABLE     m   CREATE TABLE public.salle (
    id integer NOT NULL,
    nb_place integer NOT NULL,
    nom text NOT NULL
);
    DROP TABLE public.salle;
       public         postgres    false    3            *          0    49152    administrateur 
   TABLE DATA               9   COPY public.administrateur (login, password) FROM stdin;
    public       postgres    false    205   �$       #          0    32768    adresse 
   TABLE DATA               8   COPY public.adresse (id, adresse, lat, lon) FROM stdin;
    public       postgres    false    198   �$       "          0    24580    college 
   TABLE DATA               P   COPY public.college (id_adr, nom, site_internet, numero_academique) FROM stdin;
    public       postgres    false    197   �'       !          0    16397    departement 
   TABLE DATA               J   COPY public.departement (id, id_college, id_responsable, nom) FROM stdin;
    public       postgres    false    196   �(       $          0    32773 
   enseignant 
   TABLE DATA               �   COPY public.enseignant (id, prise_de_fonction, id_college_principal, id_college_secondaire, id_adresse, id_dept_principal, id_dept_secondaire, id_matiere, nom, prenom, telephone, mail, login, password) FROM stdin;
    public       postgres    false    199   )       %          0    32776    etudiant 
   TABLE DATA               w   COPY public.etudiant (id, annee_entree_college, id_college, nom, prenom, mail, telephone, login, password) FROM stdin;
    public       postgres    false    200   �)       &          0    32779    matiere 
   TABLE DATA               4   COPY public.matiere (id, id_salle, nom) FROM stdin;
    public       postgres    false    201   C*       )          0    32808    matiere_etudiant 
   TABLE DATA               C   COPY public.matiere_etudiant (id_matiere, id_etudiant) FROM stdin;
    public       postgres    false    204   j*       (          0    32785    note 
   TABLE DATA               ?   COPY public.note (valeur, id_etudiant, id_matiere) FROM stdin;
    public       postgres    false    203   �*       '          0    32782    salle 
   TABLE DATA               2   COPY public.salle (id, nb_place, nom) FROM stdin;
    public       postgres    false    202   �*       �
           2606    49159 "   administrateur administrateur_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.administrateur
    ADD CONSTRAINT administrateur_pkey PRIMARY KEY (login);
 L   ALTER TABLE ONLY public.administrateur DROP CONSTRAINT administrateur_pkey;
       public         postgres    false    205            �
           2606    32772    adresse adresse_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.adresse
    ADD CONSTRAINT adresse_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.adresse DROP CONSTRAINT adresse_pkey;
       public         postgres    false    198            �
           2606    40961    college college_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.college
    ADD CONSTRAINT college_pkey PRIMARY KEY (numero_academique);
 >   ALTER TABLE ONLY public.college DROP CONSTRAINT college_pkey;
       public         postgres    false    197            �
           2606    24579    departement departements_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.departement
    ADD CONSTRAINT departements_pkey PRIMARY KEY (id);
 G   ALTER TABLE ONLY public.departement DROP CONSTRAINT departements_pkey;
       public         postgres    false    196            �
           2606    32794    enseignant enseignant_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.enseignant
    ADD CONSTRAINT enseignant_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.enseignant DROP CONSTRAINT enseignant_pkey;
       public         postgres    false    199            �
           2606    32799    etudiant etudiant_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.etudiant
    ADD CONSTRAINT etudiant_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.etudiant DROP CONSTRAINT etudiant_pkey;
       public         postgres    false    200            �
           2606    32804    matiere matiere_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.matiere
    ADD CONSTRAINT matiere_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.matiere DROP CONSTRAINT matiere_pkey;
       public         postgres    false    201            �
           2606    32815    salle salle_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.salle
    ADD CONSTRAINT salle_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.salle DROP CONSTRAINT salle_pkey;
       public         postgres    false    202            *      x�KL����L�\1z\\\ 4�      #   �  x��T�n�0����-Kd�)S���	��0��S&&4e����N�6���?֣d��P��&���w��	+�w�������s��P����=w:_Y�y��oƈ�0�ߣ"\��&�s�
VR�����;���>�
�*�7�3;�0�n�a*)����C� թ7��Mh|"D)MB��σ�?�$��^č���e�o��������6���0%d/�y�Ϻh�'Z��U�U��.Fm���)��`W� �����-�(��5}q~�l�ތ�a��&_���������09��5�� �����V�ۅ������8(�l ���?�~XضEa�?"�@v��H
�1_�=�� ��Xg�� ���{]x��m�>���;ƉP��ή��l{#x�����כ�g?��6o#� Bw6����O^�rl�-ؔ�|mr�NhO�aǜ\��A/6�D�/̫�Xӽ����qls� S�⑦�0���
V�$�P�2�뷞���j��GS��j$�>,�ִ����m}��	����&5Q�(���H�+EϘ��V���K���a/�B�� �*��,ۦ=%��H�����IQʂW�ɌSr�����v���L�Bʔ=�_���@"E�'JI�Y�qFX	�����)�.��f*Q ����Ƨ rTAv���G���vꑜb;�����L3^��K�Xwi�v�')��/z���ЅAU�FҊ���Be9-��I�e ���|      "     x�e�=o�0���Wx�T����D��2e1�)X5v��@�}q���=z�CGPJe����w�~�>ag۲'뵑�	c�?�`
�g���Y3K1�o6N��9Y�9�ٺ�{��J�ɟ�9��$��z6l��+\O���7�kt�8X�zq�W�n���TZ��h�DS�Eâ�w��"�
��S_�Sf�)��W^�wR��B%}�Z���H|�J5�JR>���=9�K��=\�������`ӷ���"�2�j�      !   !   x�3�42�4�tq��219]B��b���� AZ�      $   �   x�-�M
�0�דSx��L�b���t�(�]I7RF�B�1B�o*����Zs����E�C�޴}k��7>Q�d,����ӆ����4ޕ��1���>LB�rQF�T� ��>v��"��!eU
�vڣ�Bm'bC��ݘ*�      %   �   x�3�4204�5��50�4��tqU��t,�����K���/-KM,U�M��Q�R��CRsR2��RA��.�� ].�>��N����\F@��&�r���y��p�����`lhh�`nh��UW���]�R��Tja����� �|.&      &      x�3�4��M,�(����� �$      )      x������ � �      (      x������ � �      '      x�3�41��100����� J;     