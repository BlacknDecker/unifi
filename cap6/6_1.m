function A = matr_sparsa_es1(n)

    if n <= 10
        error('n deve essere > 10')
    end

    ij = ones(n, 1) * 4;
    ij1 = ones(n, 1) * -1;
    ij10 = ij1;

    B = [ij ij1 ij1 ij10 ij10];
    d = [0, 1, -1, 10, -10];

    A = spdiags(B, d, n, n);
end
