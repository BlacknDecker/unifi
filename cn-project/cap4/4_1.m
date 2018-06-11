% esempio con f(x) = pi^x
xi = [0,1,2];
fi = [1, 3.14, 9.87];
y = lagrange(xi, fi, 1.5);

%
% input:
%   xi - vettore dei punti di ascissa
%   fi - vettore dei valori di f(x)
%   x  - vettore di punti in cui calcolare f(x)
% output:
%   y  - valore di f(x)
%
function y = lagrange(xi, fi, x)
    if length(xi) ~= length(fi)
        error('xi e fi hanno lunghezza diversa!')
    end
    y = 0.;
    for i = 1:length(xi)
        y = y + fi(i) * l_k_n(x, xi, i);
    end
end

function lkn = l_k_n(x, xi, k)
    lkn = zeros(length(x), 1)' + 1;
    for i = 1:length(xi)
        if i ~= k
            pterm = (x - xi(i)) / (xi(k) - xi(i));
            lkn = prod([lkn; pterm], 1);
        end
    end
end
