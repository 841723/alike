------------------------------------------------------------------------
--  expresiones_integer.al
--  Todas son correctas
------------------------------------------------------------------------
procedure exps is
	i1, i2, i3, i4, i5, idx: integer;
	arr: array(+1..+10) of integer;
	numFormulas, numCorrectas: integer;
------------------------------------------------------------------------	
procedure evalua(found, expected: integer; numFormulas, numTrues: ref integer; idx:integer) is
begin
	if found = expected then
		numTrues := numTrues + 1;
	else
		put_line("l:",idx," -> INCORRECTA : found ", found, "   expected ", expected);
	end if;
	numFormulas := numFormulas + 1;
end;
------------------------------------------------------------------------
begin
	put_line("-----------------------------------------------------");

	numFormulas := 0;
	numCorrectas := 0;
	i1 := 1; i2 := 2; i3 := 3; i4 := 4; i5 := 5;
    arr(1) := 1; arr(2) := 2; arr(3) := 3; arr(4) := 4;
	idx := 29;

	evalua(-1, -1, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(2 + 2, 4, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(9 mod 5, 4, numFormulas, numCorrectas,idx); idx:=idx+1;
	idx:=idx+1; --evalua(-9 mod -5, -4, numFormulas, numCorrectas,idx); idx:=idx+1; --DEBE FALLAR POR EL MENOS '-'
	evalua(2 * 2 * 2 * 2, 16, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(10 * 10 + 2, 102, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(2 + 10 * 10, 102, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(-2 + 5 / 5 * 2, 0, numFormulas, numCorrectas,idx); idx:=idx+1; --MAL
	evalua(6 / 2 * 4 / 2, 6, numFormulas, numCorrectas,idx); idx:=idx+1; --MAL
	evalua((2 + 2) / 3, 1, numFormulas, numCorrectas,idx); idx:=idx+1; 
	evalua(-5 + (1 * (4 / 2)), -3, numFormulas, numCorrectas,idx); idx:=idx+1; --MAL
	evalua(10 / 4 / 2, 1, numFormulas, numCorrectas,idx); idx:=idx+1; --MAL
	evalua(-i1, -1, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua(i1 + i2, 3, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua(i1 * i2 + i3, 5, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua((i3 + i4 + 5) / i5 + 2, 4, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua((i3 + i4 + 5) / (i5 + 2), 1, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua((i1 + i2) mod 5, 3, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua((i3 + 102) mod 5, 0, numFormulas, numCorrectas,idx); idx:=idx+1;
    idx:=idx+1; --evalua(-i2 + (12 / -(i3 + 2)), -4, numFormulas, numCorrectas,idx); idx:=idx+1; --MAL --DEBE FALLAR POR EL MENOS '-'
	evalua(arr(1) + arr(2), 3, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua(arr(i4) + arr(i3), 7, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua(arr(i2 + i1) + arr(i4 / 2), 5, numFormulas, numCorrectas,idx); idx:=idx+1;
    evalua(arr(arr(2)) + arr(arr(2) + 5), 2, numFormulas, numCorrectas,idx); idx:=idx+1;
	evalua(arr(arr(2)) * arr(arr(2)) / arr(arr(2)), 2, numFormulas, numCorrectas,idx); idx:=idx+1;

	----------------------------------------------------------------------
	put_line("Debería haber llegado aquí sin problemas léxicos o sintácticos.");
	put_line("De las ", numFormulas, " fórmulas, ", numCorrectas,
			" han dado el resultado correcto.");
end;