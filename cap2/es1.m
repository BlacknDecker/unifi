fun = @(x) (x)^3 - 4*(x)^2 + 5*x -2;
tolx = 1/10; % valori sempre minori
[zero, i] = bisezione(fun, 0, 3, tolx);

% input: funzione, intervallo di confidenza [a,b] e tolleranza
% output: radice approssimata e numero di iterazioni eseguite
function [radice,iterazioni]=bisezione(f,a,b,tolx)
    iterazioni=0;
    fa = feval(f, a);
    fb = feval(f, b);
    radice = (a+b)/2;
    fx = feval(f, radice);
    imax = ceil(log2(b-a) - log2(tolx));

    if(fa*fb > 0)
        disp('ipotesi non verificata')
    else
        while(true)
            if (iterazioni>imax)
                break
            end

            iterazioni=iterazioni+1;
            f1x = abs((fb-fa)/(b-a)); %approx derivata prima

            if(abs(fx)<=tolx*f1x)
                break
            end

            if(fa*fx<0)
                b=radice;
                fb=fx;
            else
                a=radice;
                fa=fx;
            end

            radice = (a+b)/2;
            fx = feval(f, radice);

        end
    end
end
