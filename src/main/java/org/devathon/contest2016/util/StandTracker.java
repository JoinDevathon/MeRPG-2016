package org.devathon.contest2016.util;

import com.google.common.collect.Sets;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.spigotmc.AsyncCatcher;

import java.util.*;

/**
 * @author Jaxon A Brown
 */
public class StandTracker extends EntityTrackerEntry {
    private final Entity tracker;
    private final int e;
    private int f;
    private final int g;
    private long xLoc;
    private long yLoc;
    private long zLoc;
    private int yRot;
    private int xRot;
    private int headYaw;
    private double n;
    private double o;
    private double p;
    public int a;
    private double q;
    private double r;
    private double s;
    private boolean isMoving;
    private final boolean u;
    private int v;
    private List<Entity> w = Collections.emptyList();
    private boolean x;
    private boolean y;
    public boolean b;
    public final Set<EntityPlayer> trackedPlayers = Sets.newHashSet();

    public StandTracker(Entity entity) {
        super(entity, 160, 160, 1, true);
        this.tracker = entity;
        this.e = 160;
        this.f = 160;
        this.g = 1;
        this.u = true;
        this.xLoc = EntityTracker.a(entity.locX);
        this.yLoc = EntityTracker.a(entity.locY);
        this.zLoc = EntityTracker.a(entity.locZ);
        this.yRot = MathHelper.d(entity.yaw * 256.0F / 360.0F);
        this.xRot = MathHelper.d(entity.pitch * 256.0F / 360.0F);
        this.headYaw = MathHelper.d(entity.getHeadRotation() * 256.0F / 360.0F);
        this.y = entity.onGround;
    }

    public boolean equals(Object object) {
        return super.equals(object);
    }

    public int hashCode() {
        return this.tracker.getId();
    }

    public void track(List<EntityHuman> list) {
        this.b = false;
        if(!this.isMoving || this.tracker.e(this.q, this.r, this.s) > 16.0D) {
            this.q = this.tracker.locX;
            this.r = this.tracker.locY;
            this.s = this.tracker.locZ;
            this.isMoving = true;
            this.b = true;
            this.scanPlayers(list);
        }

        List list1 = this.tracker.bx();
        if(!list1.equals(this.w)) {
            this.w = list1;
            this.broadcast(new PacketPlayOutMount(this.tracker));
        }

        if(this.tracker instanceof EntityItemFrame) {
            EntityItemFrame cancelled = (EntityItemFrame)this.tracker;
            ItemStack player = cancelled.getItem();
            if(this.a % 10 == 0 && player != null && player.getItem() instanceof ItemWorldMap) {
                WorldMap velocity = Items.FILLED_MAP.getSavedMap(player, this.tracker.world);
                Iterator event = this.trackedPlayers.iterator();

                while(event.hasNext()) {
                    EntityHuman entityhuman = (EntityHuman)event.next();
                    EntityPlayer entityplayer = (EntityPlayer)entityhuman;
                    velocity.a(entityplayer, player);
                    Packet packet = Items.FILLED_MAP.a(player, this.tracker.world, entityplayer);
                    if(packet != null) {
                        entityplayer.playerConnection.sendPacket(packet);
                    }
                }
            }

            this.d();
        }

        if(this.a % this.g == 0 || this.tracker.impulse || this.tracker.getDataWatcher().a()) {
            int var36;
            if(this.tracker.isPassenger()) {
                var36 = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
                int var38 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
                boolean var40 = Math.abs(var36 - this.yRot) >= 1 || Math.abs(var38 - this.xRot) >= 1;
                if(var40) {
                    this.broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte)var36, (byte)var38, this.tracker.onGround));
                    this.yRot = var36;
                    this.xRot = var38;
                }

                this.xLoc = EntityTracker.a(this.tracker.locX);
                this.yLoc = EntityTracker.a(this.tracker.locY);
                this.zLoc = EntityTracker.a(this.tracker.locZ);
                this.d();
                this.x = true;
            } else {
                ++this.v;
                long k = EntityTracker.a(this.tracker.locX);
                long l = EntityTracker.a(this.tracker.locY);
                long i1 = EntityTracker.a(this.tracker.locZ);
                int j1 = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
                int k1 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
                long l1 = k - this.xLoc;
                long i2 = l - this.yLoc;
                long j2 = i1 - this.zLoc;
                Object object = null;
                boolean flag1 = l1 * l1 + i2 * i2 + j2 * j2 >= 128L || this.a % 60 == 0;
                boolean flag2 = Math.abs(j1 - this.yRot) >= 1 || Math.abs(k1 - this.xRot) >= 1;
                if(flag1) {
                    this.xLoc = k;
                    this.yLoc = l;
                    this.zLoc = i1;
                }

                if(flag2) {
                    this.yRot = j1;
                    this.xRot = k1;
                }

                if(this.a > 0 || this.tracker instanceof EntityArrow) {
                    this.y = this.tracker.onGround;
                    this.v = 0;
                    if(this.tracker instanceof EntityPlayer) {
                        this.scanPlayers(new ArrayList(this.trackedPlayers));
                    }

                    this.c();
                    object = new PacketPlayOutEntityTeleport(this.tracker);
                }

                boolean flag3 = this.u;
                if(this.tracker instanceof EntityLiving && ((EntityLiving)this.tracker).cG()) {
                    flag3 = true;
                }

                if(flag3) {
                    double d0 = this.tracker.motX - this.n;
                    double d1 = this.tracker.motY - this.o;
                    double d2 = this.tracker.motZ - this.p;
                    double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                    if(d4 > 4.0E-4D || d4 > 0.0D && this.tracker.motX == 0.0D && this.tracker.motY == 0.0D && this.tracker.motZ == 0.0D) {
                        this.n = this.tracker.motX;
                        this.o = this.tracker.motY;
                        this.p = this.tracker.motZ;
                        this.broadcast(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.n, this.o, this.p));
                    }
                }

                if(object != null) {
                    this.broadcast((Packet)object);
                }

                this.d();
                this.x = false;
            }

            var36 = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
            if(Math.abs(var36 - this.headYaw) >= 1) {
                this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)var36));
                this.headYaw = var36;
            }

            this.tracker.impulse = false;
        }

        ++this.a;
        if(this.tracker.velocityChanged) {
            boolean var37 = false;
            if(this.tracker instanceof EntityPlayer) {
                Player var39 = (Player)this.tracker.getBukkitEntity();
                org.bukkit.util.Vector var41 = var39.getVelocity();
                PlayerVelocityEvent var42 = new PlayerVelocityEvent(var39, var41.clone());
                this.tracker.world.getServer().getPluginManager().callEvent(var42);
                if(var42.isCancelled()) {
                    var37 = true;
                } else if(!var41.equals(var42.getVelocity())) {
                    var39.setVelocity(var42.getVelocity());
                }
            }

            if(!var37) {
                this.broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
            }

            this.tracker.velocityChanged = false;
        }

    }

    private void d() {
        DataWatcher datawatcher = this.tracker.getDataWatcher();
        if(datawatcher.a()) {
            this.broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
        }

        if(this.tracker instanceof EntityLiving) {
            AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
            Set set = attributemapserver.getAttributes();
            if(!set.isEmpty()) {
                if(this.tracker instanceof EntityPlayer) {
                    ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(set, false);
                }

                this.broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), set));
            }

            set.clear();
        }

    }

    public void broadcast(Packet<?> packet) {
        Iterator iterator = this.trackedPlayers.iterator();

        while(iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            entityplayer.playerConnection.sendPacket(packet);
        }

    }

    public void broadcastIncludingSelf(Packet<?> packet) {
        this.broadcast(packet);
        if(this.tracker instanceof EntityPlayer) {
            ((EntityPlayer)this.tracker).playerConnection.sendPacket(packet);
        }

    }

    public void a() {
        Iterator iterator = this.trackedPlayers.iterator();

        while(iterator.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            this.tracker.c(entityplayer);
            entityplayer.c(this.tracker);
        }

    }

    public void a(EntityPlayer entityplayer) {
        if(this.trackedPlayers.contains(entityplayer)) {
            this.tracker.c(entityplayer);
            entityplayer.c(this.tracker);
            this.trackedPlayers.remove(entityplayer);
        }

    }

    public void updatePlayer(EntityPlayer entityplayer) {
        AsyncCatcher.catchOp("player tracker update");
        if(entityplayer != this.tracker) {
            if(this.c(entityplayer)) {
                if(!this.trackedPlayers.contains(entityplayer) && (this.e(entityplayer) || this.tracker.attachedToPlayer)) {
                    if(this.tracker instanceof EntityPlayer) {
                        CraftPlayer packet = ((EntityPlayer)this.tracker).getBukkitEntity();
                        if(!entityplayer.getBukkitEntity().canSee(packet)) {
                            return;
                        }
                    }

                    entityplayer.removeQueue.remove(Integer.valueOf(this.tracker.getId()));
                    this.trackedPlayers.add(entityplayer);
                    Packet var9 = this.e();
                    entityplayer.playerConnection.sendPacket(var9);
                    if(!this.tracker.getDataWatcher().d()) {
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
                    }

                    boolean flag = this.u;
                    if(this.tracker instanceof EntityLiving) {
                        AttributeMapServer entityliving = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
                        Collection iterator = entityliving.c();
                        if(this.tracker.getId() == entityplayer.getId()) {
                            ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(iterator, false);
                        }

                        if(!iterator.isEmpty()) {
                            entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(this.tracker.getId(), iterator));
                        }

                        if(((EntityLiving)this.tracker).cG()) {
                            flag = true;
                        }
                    }

                    this.n = this.tracker.motX;
                    this.o = this.tracker.motY;
                    this.p = this.tracker.motZ;
                    if(flag && !(var9 instanceof PacketPlayOutSpawnEntityLiving)) {
                        entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.tracker.motX, this.tracker.motY, this.tracker.motZ));
                    }

                    if(this.tracker instanceof EntityLiving) {
                        EnumItemSlot[] var10 = EnumItemSlot.values();
                        int var13 = var10.length;

                        for(int mobeffect = 0; mobeffect < var13; ++mobeffect) {
                            EnumItemSlot enumitemslot = var10[mobeffect];
                            ItemStack itemstack = ((EntityLiving)this.tracker).getEquipment(enumitemslot);
                            if(itemstack != null) {
                                entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(this.tracker.getId(), enumitemslot, itemstack));
                            }
                        }
                    }

                    if(this.tracker instanceof EntityHuman) {
                        EntityHuman var11 = (EntityHuman)this.tracker;
                        if(var11.isSleeping()) {
                            entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(var11, new BlockPosition(this.tracker)));
                        }
                    }

                    this.headYaw = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
                    this.broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)this.headYaw));
                    if(this.tracker instanceof EntityLiving) {
                        EntityLiving var12 = (EntityLiving)this.tracker;
                        Iterator var14 = var12.getEffects().iterator();

                        while(var14.hasNext()) {
                            MobEffect var15 = (MobEffect)var14.next();
                            entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.tracker.getId(), var15));
                        }
                    }

                    this.tracker.b(entityplayer);
                    entityplayer.d(this.tracker);
                }
            } else if(this.trackedPlayers.contains(entityplayer)) {
                this.trackedPlayers.remove(entityplayer);
                this.tracker.c(entityplayer);
                entityplayer.c(this.tracker);
            }
        }

    }

    public boolean c(EntityPlayer entityplayer) {
        double d0 = entityplayer.locX - (double)this.xLoc / 4096.0D;
        double d1 = entityplayer.locZ - (double)this.zLoc / 4096.0D;
        int i = Math.min(this.e, this.f);
        return d0 >= (double)(-i) && d0 <= (double)i && d1 >= (double)(-i) && d1 <= (double)i && this.tracker.a(entityplayer);
    }

    private boolean e(EntityPlayer entityplayer) {
        return entityplayer.x().getPlayerChunkMap().a(entityplayer, this.tracker.ac, this.tracker.ae);
    }

    public void scanPlayers(List<EntityHuman> list) {
        for(int i = 0; i < list.size(); ++i) {
            this.updatePlayer((EntityPlayer)list.get(i));
        }

    }

    private Packet<?> e() {
        if(this.tracker.dead) {
            return null;
        } else if(this.tracker instanceof EntityItem) {
            return new PacketPlayOutSpawnEntity(this.tracker, 2, 1);
        } else if(this.tracker instanceof EntityPlayer) {
            return new PacketPlayOutNamedEntitySpawn((EntityHuman)this.tracker);
        } else if(this.tracker instanceof EntityMinecartAbstract) {
            EntityMinecartAbstract entity2 = (EntityMinecartAbstract)this.tracker;
            return new PacketPlayOutSpawnEntity(this.tracker, 10, entity2.v().a());
        } else if(this.tracker instanceof EntityBoat) {
            return new PacketPlayOutSpawnEntity(this.tracker, 1);
        } else if(this.tracker instanceof IAnimal) {
            this.headYaw = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
            return new PacketPlayOutSpawnEntityLiving((EntityLiving)this.tracker);
        } else if(this.tracker instanceof EntityFishingHook) {
            EntityHuman entity1 = ((EntityFishingHook)this.tracker).owner;
            return new PacketPlayOutSpawnEntity(this.tracker, 90, entity1 != null?entity1.getId():this.tracker.getId());
        } else {
            Entity entity;
            if(this.tracker instanceof EntitySpectralArrow) {
                entity = ((EntitySpectralArrow)this.tracker).shooter;
                return new PacketPlayOutSpawnEntity(this.tracker, 91, 1 + (entity != null?entity.getId():this.tracker.getId()));
            } else if(this.tracker instanceof EntityTippedArrow) {
                entity = ((EntityArrow)this.tracker).shooter;
                return new PacketPlayOutSpawnEntity(this.tracker, 60, 1 + (entity != null?entity.getId():this.tracker.getId()));
            } else if(this.tracker instanceof EntitySnowball) {
                return new PacketPlayOutSpawnEntity(this.tracker, 61);
            } else if(this.tracker instanceof EntityPotion) {
                return new PacketPlayOutSpawnEntity(this.tracker, 73);
            } else if(this.tracker instanceof EntityThrownExpBottle) {
                return new PacketPlayOutSpawnEntity(this.tracker, 75);
            } else if(this.tracker instanceof EntityEnderPearl) {
                return new PacketPlayOutSpawnEntity(this.tracker, 65);
            } else if(this.tracker instanceof EntityEnderSignal) {
                return new PacketPlayOutSpawnEntity(this.tracker, 72);
            } else if(this.tracker instanceof EntityFireworks) {
                return new PacketPlayOutSpawnEntity(this.tracker, 76);
            } else if(this.tracker instanceof EntityFireball) {
                EntityFireball entityleash4 = (EntityFireball)this.tracker;
                PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
                byte b0 = 63;
                if(this.tracker instanceof EntitySmallFireball) {
                    b0 = 64;
                } else if(this.tracker instanceof EntityDragonFireball) {
                    b0 = 93;
                } else if(this.tracker instanceof EntityWitherSkull) {
                    b0 = 66;
                }

                if(entityleash4.shooter != null) {
                    packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, ((EntityFireball)this.tracker).shooter.getId());
                } else {
                    packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, 0);
                }

                packetplayoutspawnentity.a((int)(entityleash4.dirX * 8000.0D));
                packetplayoutspawnentity.b((int)(entityleash4.dirY * 8000.0D));
                packetplayoutspawnentity.c((int)(entityleash4.dirZ * 8000.0D));
                return packetplayoutspawnentity;
            } else if(this.tracker instanceof EntityShulkerBullet) {
                PacketPlayOutSpawnEntity entityleash3 = new PacketPlayOutSpawnEntity(this.tracker, 67, 0);
                entityleash3.a((int)(this.tracker.motX * 8000.0D));
                entityleash3.b((int)(this.tracker.motY * 8000.0D));
                entityleash3.c((int)(this.tracker.motZ * 8000.0D));
                return entityleash3;
            } else if(this.tracker instanceof EntityEgg) {
                return new PacketPlayOutSpawnEntity(this.tracker, 62);
            } else if(this.tracker instanceof EntityTNTPrimed) {
                return new PacketPlayOutSpawnEntity(this.tracker, 50);
            } else if(this.tracker instanceof EntityEnderCrystal) {
                return new PacketPlayOutSpawnEntity(this.tracker, 51);
            } else if(this.tracker instanceof EntityFallingBlock) {
                EntityFallingBlock entityleash2 = (EntityFallingBlock)this.tracker;
                return new PacketPlayOutSpawnEntity(this.tracker, 70, Block.getCombinedId(entityleash2.getBlock()));
            } else if(this.tracker instanceof EntityArmorStand) {
                return new PacketPlayOutSpawnEntity(this.tracker, 78);
            } else if(this.tracker instanceof EntityPainting) {
                return new PacketPlayOutSpawnEntityPainting((EntityPainting)this.tracker);
            } else if(this.tracker instanceof EntityItemFrame) {
                EntityItemFrame entityleash1 = (EntityItemFrame)this.tracker;
                return new PacketPlayOutSpawnEntity(this.tracker, 71, entityleash1.direction.get2DRotationValue(), entityleash1.getBlockPosition());
            } else if(this.tracker instanceof EntityLeash) {
                EntityLeash entityleash = (EntityLeash)this.tracker;
                return new PacketPlayOutSpawnEntity(this.tracker, 77, 0, entityleash.getBlockPosition());
            } else if(this.tracker instanceof EntityExperienceOrb) {
                return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb)this.tracker);
            } else if(this.tracker instanceof EntityAreaEffectCloud) {
                return new PacketPlayOutSpawnEntity(this.tracker, 3);
            } else {
                throw new IllegalArgumentException("Don\'t know how to add " + this.tracker.getClass() + "!");
            }
        }
    }

    public void clear(EntityPlayer entityplayer) {
        AsyncCatcher.catchOp("player tracker clear");
        if(this.trackedPlayers.contains(entityplayer)) {
            this.trackedPlayers.remove(entityplayer);
            this.tracker.c(entityplayer);
            entityplayer.c(this.tracker);
        }

    }

    public Entity b() {
        return this.tracker;
    }

    public void a(int i) {
        this.f = i;
    }

    public void c() {
        this.isMoving = false;
    }
}
