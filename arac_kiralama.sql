PGDMP  "    +                }            arac_kiralama    16.8    16.8     .           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            /           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            0           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            1           1262    16558    arac_kiralama    DATABASE     s   CREATE DATABASE arac_kiralama WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en-US';
    DROP DATABASE arac_kiralama;
                postgres    false            �            1259    16560    arac    TABLE     �  CREATE TABLE public.arac (
    id integer NOT NULL,
    plaka character varying(20) NOT NULL,
    marka character varying(50) NOT NULL,
    model character varying(50) NOT NULL,
    yakit_turu character varying(20) NOT NULL,
    vites_turu character varying(20) NOT NULL,
    gunluk_ucret numeric(10,2) NOT NULL,
    durum character varying(20) DEFAULT 'Müsait'::character varying
);
    DROP TABLE public.arac;
       public         heap    postgres    false            �            1259    16559    arac_id_seq    SEQUENCE     �   CREATE SEQUENCE public.arac_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.arac_id_seq;
       public          postgres    false    216            2           0    0    arac_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.arac_id_seq OWNED BY public.arac.id;
          public          postgres    false    215            �            1259    16586    kiralama    TABLE     J  CREATE TABLE public.kiralama (
    id integer NOT NULL,
    arac_id integer,
    isim character varying(100),
    soyisim character varying(100),
    tc character varying(11),
    iletisim character varying(100),
    tarih timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    teslim_tarihi date,
    kiralama_tarihi date
);
    DROP TABLE public.kiralama;
       public         heap    postgres    false            �            1259    16585    kiralama_id_seq    SEQUENCE     �   CREATE SEQUENCE public.kiralama_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.kiralama_id_seq;
       public          postgres    false    220            3           0    0    kiralama_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.kiralama_id_seq OWNED BY public.kiralama.id;
          public          postgres    false    219            �            1259    16577 	   kullanici    TABLE     �   CREATE TABLE public.kullanici (
    id integer NOT NULL,
    kullanici_adi character varying(50) NOT NULL,
    sifre character varying(50) NOT NULL,
    rol character varying(10) NOT NULL,
    email character varying(100)
);
    DROP TABLE public.kullanici;
       public         heap    postgres    false            �            1259    16576    kullanici_id_seq    SEQUENCE     �   CREATE SEQUENCE public.kullanici_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.kullanici_id_seq;
       public          postgres    false    218            4           0    0    kullanici_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.kullanici_id_seq OWNED BY public.kullanici.id;
          public          postgres    false    217            �           2604    16563    arac id    DEFAULT     b   ALTER TABLE ONLY public.arac ALTER COLUMN id SET DEFAULT nextval('public.arac_id_seq'::regclass);
 6   ALTER TABLE public.arac ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �           2604    16589    kiralama id    DEFAULT     j   ALTER TABLE ONLY public.kiralama ALTER COLUMN id SET DEFAULT nextval('public.kiralama_id_seq'::regclass);
 :   ALTER TABLE public.kiralama ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    220    220            �           2604    16580    kullanici id    DEFAULT     l   ALTER TABLE ONLY public.kullanici ALTER COLUMN id SET DEFAULT nextval('public.kullanici_id_seq'::regclass);
 ;   ALTER TABLE public.kullanici ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    218    218            '          0    16560    arac 
   TABLE DATA           d   COPY public.arac (id, plaka, marka, model, yakit_turu, vites_turu, gunluk_ucret, durum) FROM stdin;
    public          postgres    false    216   N       +          0    16586    kiralama 
   TABLE DATA           s   COPY public.kiralama (id, arac_id, isim, soyisim, tc, iletisim, tarih, teslim_tarihi, kiralama_tarihi) FROM stdin;
    public          postgres    false    220   �       )          0    16577 	   kullanici 
   TABLE DATA           I   COPY public.kullanici (id, kullanici_adi, sifre, rol, email) FROM stdin;
    public          postgres    false    218   �       5           0    0    arac_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.arac_id_seq', 7, true);
          public          postgres    false    215            6           0    0    kiralama_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.kiralama_id_seq', 4, true);
          public          postgres    false    219            7           0    0    kullanici_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.kullanici_id_seq', 7, true);
          public          postgres    false    217            �           2606    16565    arac arac_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.arac
    ADD CONSTRAINT arac_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.arac DROP CONSTRAINT arac_pkey;
       public            postgres    false    216            �           2606    16592    kiralama kiralama_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.kiralama
    ADD CONSTRAINT kiralama_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.kiralama DROP CONSTRAINT kiralama_pkey;
       public            postgres    false    220            �           2606    16584 %   kullanici kullanici_kullanici_adi_key 
   CONSTRAINT     i   ALTER TABLE ONLY public.kullanici
    ADD CONSTRAINT kullanici_kullanici_adi_key UNIQUE (kullanici_adi);
 O   ALTER TABLE ONLY public.kullanici DROP CONSTRAINT kullanici_kullanici_adi_key;
       public            postgres    false    218            �           2606    16582    kullanici kullanici_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.kullanici
    ADD CONSTRAINT kullanici_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.kullanici DROP CONSTRAINT kullanici_pkey;
       public            postgres    false    218            �           2606    16593    kiralama kiralama_arac_id_fkey    FK CONSTRAINT     |   ALTER TABLE ONLY public.kiralama
    ADD CONSTRAINT kiralama_arac_id_fkey FOREIGN KEY (arac_id) REFERENCES public.arac(id);
 H   ALTER TABLE ONLY public.kiralama DROP CONSTRAINT kiralama_arac_id_fkey;
       public          postgres    false    4751    216    220            '   �   x�3�40���60��M-JNMI-�uJͫ�t755��2�8�K�sK2�9M�8�3�s�l��2�����lh����������.�f�(_�a�%��.�U�9���y�@�M�!P��_0�NG��)�Z�LQ���qqq PsIS      +   �   x����1Ekg�[�(vb;NGql@G��I �p4�
�b��Dra�_��!�|������e3��X�2�p�Fɵ�@<2C�PJ��[������T��q8u�a7��~[�Ĳa� QsVV�aG*h^H�����EH���,|�[�*�����0A<U:'�_xՓ����B�
�7�_��	4��ι��NZ      )   U   x�3�LL��̃��F��\��������\.c����Ԝ��TNss����"N�@irb�Rs�Č��NSS�20IE� nv&�     