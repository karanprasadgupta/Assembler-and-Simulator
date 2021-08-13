import java.util.*;

public class CO_Assignment {
    public static ArrayList<ArrayList<ArrayList<String>>> input_from_console(){
        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> input_for_error_generation = new ArrayList<ArrayList<String>>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String read = sc.nextLine();
            if (read == null || read.isEmpty()) {
                break;
            }
            //System.out.println(read);
            if(read.trim()!=""){
                ArrayList<String> sub_input = new ArrayList<>(Arrays.asList(read.trim().split("\\s+")));
                if (sub_input.get(0).equals("var")) {
                    sub_input.add(0, "variable");
                }
                else if(sub_input.get(0).substring(sub_input.get(0).length()-1).equals(":")){
                    sub_input.add(0, "label");
                }
                else {
                    sub_input.add(0, "instruction");
                }
                input.add(sub_input);
                input_for_error_generation.add(sub_input);
            }
            else {
                ArrayList<String> sub_input = new ArrayList<>(Arrays.asList(read.trim().split("\\s+")));
                input_for_error_generation.add(sub_input);
            }
        }
        ArrayList<ArrayList<ArrayList<String>>> input_arr=new ArrayList<ArrayList<ArrayList<String>>>();
        input_arr.add(input);
        input_arr.add(1,input_for_error_generation);
        return input_arr;
    }

    public static void Registers_Dict(){
        //register dictionary
        //Hashtable<String, String[]> registers = new Hashtable<String, String[]>();
        String[] R0={"000","0000000000000000"};
        globall.registers.put("R0",R0);
        String[] R1={"001","0000000000000000"};
        globall.registers.put("R1",R1);
        String[] R2={"010","0000000000000000"};
        globall.registers.put("R2",R2);
        String[] R3={"011","0000000000000000"};
        globall.registers.put("R3",R3);
        String[] R4={"100","0000000000000000"};
        globall.registers.put("R4",R4);
        String[] R5={"101","0000000000000000"};
        globall.registers.put("R5",R5);
        String[] R6={"110","0000000000000000"};
        globall.registers.put("R6",R6);
        String[] FLAGS={"111","0000000000000000"};
        globall.registers.put("FLAGS",FLAGS);

    }

    public static Hashtable<String, String[]> Opcode_Dict(){
        //opcode dictionary
        Hashtable<String, String[]> opcode = new Hashtable<String, String[]>();
        String[] add={"00000","A"};
        opcode.put("add",add);
        String[] sub={"00001","A"};
        opcode.put("sub",sub);
        String[] mul={"00110","A"};
        opcode.put("mul",mul);
        String[] div={"00111","C"};
        opcode.put("div",div);
        String[] xor={"01010","A"};
        opcode.put("xor",xor);
        String[] or={"01011","A"};
        opcode.put("or",or);
        String[] and={"01100","A"};
        opcode.put("and",and);
        String[] movimm={"00010","B"};
        opcode.put("mov1",movimm);
        String[] movreg={"00011","C"};
        opcode.put("mov2",movreg);
        String[] rs={"01000","B"};
        opcode.put("rs",rs);
        String[] ls={"01001","B"};
        opcode.put("ls",ls);
        String[] not={"01101","C"};
        opcode.put("not",not);
        String[] cmp={"01110","C"};
        opcode.put("cmp",cmp);
        String[] ld={"00100","D"};
        opcode.put("ld",ld);
        String[] st={"00101","D"};
        opcode.put("st",st);
        String[] jmp={"01111","E"};
        opcode.put("jmp",jmp);
        String[] jlt={"10000","E"};
        opcode.put("jlt",jlt);
        String[] jgt={"10001","E"};
        opcode.put("jgt",jgt);
        String[] je={"10010","E"};
        opcode.put("je",je);
        String[] hlt={"10011","F"};
        opcode.put("hlt",hlt);
        return opcode;
    }

    public static Hashtable<String, String> Label_memory_Dict(ArrayList<ArrayList<String>> input){
        //Label Dictionary Stores {label name : memory address
        Hashtable<String, String> label_dict = new Hashtable<String, String>();
        int memory_counter=0;
        for (int i=0;i<input.size();i++){
            if (input.get(i).get(0).equals("instruction"))
                memory_counter++;
            else if(input.get(i).get(0).equals("label")){
                String binaryresult = Integer.toBinaryString(memory_counter);
                String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
                label_dict.put(input.get(i).get(1).substring(0,input.get(i).get(1).length()-1),BinaryresultWithPadding);
                memory_counter++;
            }
        }
        return label_dict;
    }

    public static Hashtable<String, String> variable_memory_assigner_Dict(ArrayList<ArrayList<String>> input){

        Hashtable<String, String> variable_dict = new Hashtable<String, String>();
        int memory_counter=0;
        for (int i=0;i<input.size();i++){
            if ((input.get(i).get(0).equals("instruction")) || (input.get(i).get(0).equals("label"))){
                memory_counter++;
            }
        }
        for (int i=0;i<input.size();i++){
            if (input.get(i).get(0).equals("variable")){
                String binaryresult = Integer.toBinaryString(memory_counter);
                String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
                variable_dict.put(input.get(i).get(2),BinaryresultWithPadding);
                memory_counter++;
            }
            else
                break;
        }
        return variable_dict;
    }

    public static void input_to_machine_code(ArrayList<ArrayList<String>> input,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        for (int i=0;i<input.size();i++){
            if(input.get(i).get(0).equals("instruction")){
                instruction_to_mcode(input.get(i),opcode,variable_mem_dict,label_mem_dict );
            }
            else if(input.get(i).get(0).equals("label")){
                ArrayList<String> label_instruction=new ArrayList<String>(input.get(i));
                label_instruction.remove(0);
                label_instruction.remove(0);
                label_instruction.add(0,"instruction");
                instruction_to_mcode(label_instruction,opcode,variable_mem_dict,label_mem_dict);
            }
        }
    }

    public static void instruction_to_mcode(ArrayList<String> instruction,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        //System.out.println("Converting"+instruction.toString());
        if(instruction.get(1).equals("add") || instruction.get(1).equals("sub") || instruction.get(1).equals("mul") || instruction.get(1).equals("xor") || instruction.get(1).equals("or") || instruction.get(1).equals("and")){
            Type_A_out(instruction,opcode);
        }
        else if(instruction.size()==4 && isRegister(instruction.get(3)) && (instruction.get(1).equals("div") || instruction.get(1).equals("cmp") || instruction.get(1).equals("not") || instruction.get(1).equals("mov"))){
            Type_C_out(instruction,opcode);
        }
        else if((instruction.get(1).equals("ls") || instruction.get(1).equals("rs") || instruction.get(1).equals("mov"))){
            Type_B_out(instruction,opcode);
        }

        else if(instruction.get(1).equals("ld") || instruction.get(1).equals("st")){
            Type_D_out(instruction,opcode,variable_mem_dict);
        }
        else if(instruction.get(1).equals("jmp") || instruction.get(1).equals("jlt") || instruction.get(1).equals("jgt") || instruction.get(1).equals("je")){
            Type_E_out(instruction,opcode,label_mem_dict);
        }
        else if (instruction.get(1).equals("hlt")){
            Type_F_out(instruction,opcode);
        }
    }

    public static boolean isRegister(String S){
        if(globall.registers.get(S)!=null)
            return true;
        return false;
    }

    public static String imm_to_binary(String S){
        String binaryresult = Integer.toBinaryString(Integer.parseInt(S));
        String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
        return BinaryresultWithPadding;
    }

    public static void Type_A_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("xor") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("xor")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("and") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("and")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("or") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("or")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mul") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("mul")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("add") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("add")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("sub") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("sub")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
    }

    public static void Type_B_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        //left shift-ls
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("ls") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("ls")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
        //right shift-rs
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("rs") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("rs")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
        //move immediate-mov imm
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mov") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("mov1")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
    }

    public static void Type_C_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("not") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("not")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("cmp") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("cmp")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("div") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("div")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mov") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("mov2")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
    }

    public static void Type_D_out(ArrayList<String> instruction,Hashtable<String,String[]> opcode,Hashtable<String, String> variable_mem_dict){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("ld") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("ld")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+ variable_mem_dict.get(instruction.get(3)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("st") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("st")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+ variable_mem_dict.get(instruction.get(3)));
        }
    }

    public static void Type_E_out(ArrayList<String> instruction,Hashtable<String,String[]> opcode,Hashtable<String, String> label_mem_dict){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jmp") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("jmp")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jlt") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("jlt")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jgt") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("jgt")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("je") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("je")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
    }

    public static void Type_F_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("hlt")){
            System.out.println(opcode.get("hlt")[0]+"00000000000");
        }
    }

    public static void main(String[] args){
        ArrayList<ArrayList<ArrayList<String>>> input_arr=input_from_console();
        ArrayList<ArrayList<String>> input_for_machine_code_generation = input_arr.get(0);
        ArrayList<ArrayList<String>> input_for_error_generation = input_arr.get(1);
        Registers_Dict();
        Hashtable<String, String[]> Opcode_dict = Opcode_Dict();
        Hashtable<String, String> Label_dict = Label_memory_Dict(input_for_machine_code_generation);
        Hashtable<String, String> variable_memory_dict=variable_memory_assigner_Dict(input_for_machine_code_generation);

        input_to_machine_code(input_for_machine_code_generation,Opcode_dict,variable_memory_dict,Label_dict);

    }


}
class globall{
    public static Hashtable<String, String[]> registers = new Hashtable<String, String[]>();
    public static int[] flag=new int[16];

}
