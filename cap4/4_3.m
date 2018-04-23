% esempio con f(x) = e^x
% xi =  [0,1,2];
% fi =  [1, 2.71, 7.39];
% f1i = [1, 2.71, 7.39];
% x = 1.5;
%
% y = diff_div_hermite(xi, fi, f1i, x);

xi = [-1, 3];
fi = [1, 2];
f1i=[1, 0];

y = diff_div_hermite(xi, fi);%, f1i, x);
%
% input:
%   xi  - vettore dei punti di ascissa
%   fi  - vettore dei valori di f(x)
%   f1i - vettore dei valori di f'(x)
%   x   - punto in cui calcolare f(x)
% output:
%   y   - valore di f(x)
%

function fih = diff_div_hermite(xi, fi)
    if length(xi) ~= length(fi)
        error('x_i e f(x_i) hanno lunghezza diversa')
    end
    xih = zeros(length(xi)*2, 1);
    fih = zeros(length(fi)*2, 1);
    for i = 1:length(xi)
        xih(i+i-1) = xi(i);
        xih(i+i) = xi(i);
        fih(i+i-1) = fi(i);
        fih(i+i) = fi(i);
    end
    for i = length(xih)-1:-2:3
        fih(i) = (fih(i)-fih(i-2))/(xih(i)-xih(i-2));
    end
    for i = 2:length(xih)-1
        for j=length(xih):-1:i+1
            fih(j) = (fih(j)-fih(j-1))/(xih(j)-xih(j-i));
        end
    end
end
