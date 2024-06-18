-- Validador de palíndromos
-- Un palíndromo es una frase que se lee igual de izquierda a derecha que de derecha a izquierda
-- Se debe escribir en minúsculas y sin tildes: e.g.: "dabale arroz a la zorra el abad"
-------------------------------------------------------
procedure palindromoChecker is
-------------------------------------------------------
    frase : array(-50..50) of character;
    es_pal : boolean;
-------------------------------------------------------
procedure pedir_frase (frase : ref array(-50..50) of character) is
-------------------------------------------------------
    i : integer;
    c, entrada : character;

begin
    put_line("Introduzca una frase en minúscula, e.g.: ""reconocer""");
    c := int2char(10);
    i := -50;

    get(entrada);
    frase(i) := entrada;

    while entrada /= c loop
        i := i + 1;
        get(entrada);
        frase(i) := entrada;
    end loop;
end;
-------------------------------------------------------
procedure comprobar_palindromo (frase : ref array(-50..50) of character) is
-------------------------------------------------------
    i, j : integer;
    c : character;
    es_pal : boolean;
begin
    i := -50;
    j := -50;
    c := int2char(10);
    es_pal := true;

    while frase(i) /= c loop
        i := i + 1;
    end loop;

    i := i - 1;

    while j < i and es_pal = true loop
        -- Ignorar espacios y tabuladores
        while frase(j) = int2char(32) or frase(j) = int2char(9) loop
            j := j + 1;
        end loop;
        while frase(i) = int2char(32) or frase(i) = int2char(9) loop
            i := i - 1;
        end loop;
        if frase(j) /= frase(i) and j < i then
            es_pal := false;
        end if;
        j := j + 1;
        i := i - 1;
    end loop;

    if es_pal then
        put_line("La frase es un palíndromo.");
    else
        put_line("La frase no es un palíndromo.");
    end if;
end;
-------------------------------------------------------
begin
    pedir_frase(frase);
    comprobar_palindromo(frase);


    
end;