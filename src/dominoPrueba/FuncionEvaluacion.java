package dominoPrueba;
abstract class ComponenteEvaluacion {
    private double rangoI;
    private double rangoS;
    private double peso;
    
    public ComponenteEvaluacion(double peso, double rangoI, double rangoS) {
        this.rangoI = rangoI;
        this.rangoS = rangoS;
        this.peso = peso;
    }
    
    public abstract double evaluacion(Estado estado);

    private double normalizar(double valor, double rangoI, double rangoS) {
        return (valor-rangoI)/rangoS;
    }

    public double ejecutar(Estado estado) {
        return this.normalizar(this.evaluacion(estado), this.rangoI, this.rangoS) * this.peso;
    }

    public double getPeso() {
        return this.peso;
    }

    public double getRangoI() {
        return this.rangoI;
    }

    public double getRangoS() {
        return this.rangoS;
    }
}

public class FuncionEvaluacion {
    private ComponenteEvaluacion[] componentes;

    public FuncionEvaluacion(ComponenteEvaluacion ...componentes) {
        this.componentes = componentes;

        // Verificacion de total de peso = 1
        double totalPeso = 0;
        for(ComponenteEvaluacion c: componentes)
            totalPeso += c.getPeso();

        if(totalPeso != 1)
            System.err.println("Warning. El peso total de los componentes de evaluacion es " + totalPeso + " y deberia ser 1.");
    }

    public double ejecutar(Estado estado) {
        double total = 0;

        for(ComponenteEvaluacion componente: this.componentes)
            total += componente.ejecutar(estado);

        return total;
    }
}
