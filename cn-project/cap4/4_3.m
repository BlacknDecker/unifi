% esempio con f(x) = e^x
xi =  [0, 1, 2];
fi =  [1, 2.71, 7.39];
f1i = [1, 2.71, 7.39];
y = hermite(xi, fi, f1i, 1.5);

% input:
%   xi  - vettore delle ascisse
%   fi  - vettore delle valutazioni di f(x)
%   f1i - vettore delle valutazioni di f'(x)
%   x   - punto da valutare
% output:
%    fi - vettore riscrito con le differenze divise
function y = hermite(xi, fi, f1i, x)
    if length(xi) ~= length(fi) || length(f1i) ~= length(fi)
        error('xi, fi e f1x hanno lunghezza diversa!')
    end

    % combino opportunamente i vettori
    xih = zeros(length(xi)*2, 1);
    fih = zeros(length(fi)*2, 1);

    for i = 1:length(xi)
        xih(i+i-1) = xi(i);
        xih(i+i) = xi(i);
        fih(i+i-1) = fi(i);
        fih(i+i) = f1i(i);
    end

    dd = diff_div_herm(xih, fih);

    y = dd(1);
    for i = 2 : length(dd)
        prod = dd(i);
        for j=1:i-1
            prod = prod*(x-xih(j));
        end
        y = y + prod;
    end
end

% input:
%   fi - vettore con f(x0), f'(x0), ..., f(xn), f'(xn)
%   xi - vettore delle ascisse
% output:
%    fi - vettore riscrito con le differenze divise
function [f] = diff_div_herm(x, f)

    for i = length(x)-1 : -2 : 3
        f(i) = (f(i)-f(i-2))/(x(i)-x(i-2));
    end

    for i = 2 : length(x)-1
        for j = length(x) : -1 : i+1
            f(j) = (f(j)-f(j-1))/(x(j)-x(j-i));
        end
    end
end
